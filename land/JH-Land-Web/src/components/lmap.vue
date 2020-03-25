<template>
    <div class="map-container" id="map">
        <div class="toolBar">
            <div class="tool-item bgcolorffffff">
                <i class="icon icon-square"></i>
                <span>矩形工具</span>
            </div>
            <div class="tool-item bgcolor007dff">
                <i class="icon icon-load"></i>
                <span>数据下载</span>
            </div>
        </div>
        <!-- control控件自定义 -->
        <div class="controlBar">
            <div class="tool-item" @click="zoomIn" title="放大">
                <i class="el-icon-plus"></i>
            </div>
            <div class="tool-item" @click="zoomOut" title="缩小">
                <i class="el-icon-minus"></i>
            </div>
            <div class="tool-item" @click="resetMap" title="重置">
                <i class="el-icon-refresh"></i>
            </div>
        </div>
    </div>
</template>

<script>

    // 行政区划级别对应地图级别
    const regionLevelToZoom = {
        1: 5,
        2: 8,
        3: 9,
        4: 11,
        5: 14
    };

    export default {
        name: "Map",
        props: {
            defaultRegionId: { // 默认定位的地级市
                type: Number,
                default() {
                    return 3104000977;
                }
            },
            cropMap: { // 作物ID对应作物名称列表
                type: Object,
                default() {
                    return {};
                }
            },
            dateYear: { // 默认年份
                type: Number,
                default() {
                    /*
                    let _date = new Date();
                    return _date.getFullYear();
                     */
                    return 2019;
                }
            },
        },
        data() {
            return {
                map: null, // 地图对象
                // 地图默认配置
                mapOptions: {
                    center: [36.4431310505716, 103.846520481217], // 中心点
                    zoom: 5, // 初始级别
                },
                currentCityRegionId: null, // 当前地图的地级市行政区划编号
                currentRegionInfo: null, // 当前地图显示的行政区划属性
                cacheRegionList: {}, // 行政区划边界缓存

                regionLayerGroup: null,  // 行政区划边界图层
                regionLayerLabelGroup: null, // 行政区划边界注记图层
                landLayer: null,// 地块图层

                clickLandLayer: null, // 点击选取的地块图层
                clickPopup: null, // 点击选取的POP弹窗


                // 底图地址：天地图影像
                baseMapLayerUrl: "http://t{s}.tianditu.gov.cn/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=d7b4a2504ed9ff285644059f12613233",
                // 行政区划边界地址
                lyrRegionLayerUrl: this.baseApiPath + "/jh-map/nolog/layer/ows",
                // 获取地块图层名称地址
                getLandLayerNameUrl: this.baseApiPath + "/jh-land/nolog/rankAreaTask/findLayerName",
                // 地块TILE-WMS服务地址
                lyrTileWMSUrl: this.baseApiPath + "/jh-map/nolog/layer/wms",
                // 根据经纬度查询地块地址
                getLandByLonlat: this.baseApiPath + "/jh-land/nolog/land/findLandInfoByLonLat",
                // 根据ID查询地块地址
                getLandById: this.baseApiPath + "/jh-land/nolog/land/findLandInfoById",

            };
        },
        created() {
            // 绑定全局触发器
            this.bindTriggers();
        },
        mounted() {
            let that = this;
            // 初始化地图
            that.initMap();
        },
        methods: {
            /**
             * 初始化地图
             */
            initMap() {
                let that = this;
                that.map = that.$utils.map.createMap("map", {
                    center: that.mapOptions.center,
                    zoom: that.mapOptions.zoom,
                    attributionControl: false,
                    zoomControl: false
                });
                // 加载底图
                that.$utils.map.createTileLayer(that.map, that.baseMapLayerUrl, {
                    subdomains: ["0", "1", "2", "3", "4"]
                });
                // 定位到默认行政区划位置
                JHGEvent.trigger("Region/SelectRegion", that.defaultRegionId);
            },

            /**
             * 绑定全局触发器
             */
            bindTriggers() {
                let that = this;
                // 选择行政区划
                JHGEvent.on("Map/SelectRegion", regionInfo => {
                    if (!regionInfo) {
                        return;
                    }
                    that.initSelectRegion(regionInfo);
                    // 加载左侧列表
                    JHGEvent.trigger("Table/SelectRegion", regionInfo);
                });

                // 地块列表点击弹出POPUP
                JHGEvent.on("Map/ClickLand", rankId => {
                    if (!rankId) {
                        return;
                    }
                    let landTableName = "init_rank_" + that.dateYear + "_" + that.currentCityRegionId;
                    that.$axios.post(that.getLandById, {
                        landTableName: landTableName,
                        rankId: rankId
                    }).then(response => {
                        let result = response.data;
                        let data = result.data;
                        that.showLandPopup(data, true);
                    });
                });
            },

            /**
             * 根据行政区划定位地图并渲染内容
             */
            initSelectRegion(regionInfo) {
                let that = this;
                that.currentRegionInfo = regionInfo;
                // 设置地图位置
                let zoom = regionLevelToZoom[regionInfo.level];
                let lngLat = that.wktToLatlng(regionInfo.centroid);
                that.map.setView(lngLat, zoom);
                // 清理地图
                that.clearMap();
                // 加载并渲染行政区划边界
                that.loadSelectRegionLayer(regionInfo);
                // 行政区划为区县级别时设置当前地级市
                if (regionInfo.level == 4) {
                    that.currentCityRegionId = regionInfo.parentId;
                }
                // 如果为乡镇级别则加载地块
                if (regionInfo.level == 5) {
                    that.loadLandLayer(regionInfo);
                }
            },

            /**
             * 加载地块
             */
            loadLandLayer(regionInfo) {
                let that = this;
                // 获取地块图层名称
                let landLyrName = that.getLandLayerName(regionInfo);
                landLyrName.then(lyrName => {
                    // 加载地块图层
                    let lyrTileWMSUrl = that.lyrTileWMSUrl + "?layers=" + lyrName + "&CRS=EPSG:4326";
                    // 添加过滤参数
                    let regionId = regionInfo.regionId;
                    let filter = that.getFilterParam(regionId);
                    lyrTileWMSUrl = lyrTileWMSUrl + "&FILTER=" + filter;
                    that.landLayer = L.tileLayer.wms(lyrTileWMSUrl, {
                        layers: lyrName,
                        format: 'image/png',
                        transparent: true,
                        crs: L.CRS.EPSG4326,
                        uppercase: true
                    }).addTo(that.map);
                });
                // 注册点击事件
                that.map.on("click", that.landClick);
            },

            /**
             *  地块点击事件
             */
            landClick(evt) {
                let that = this;
                // 获取点击的经纬度坐标
                let latlng = evt.latlng;
                let lat = latlng.lat;
                let lng = latlng.lng;
                let landTableName = "init_rank_" + that.dateYear + "_" + that.currentCityRegionId;
                // 根据点击的经纬度查询地块
                that.$axios.post(that.getLandByLonlat, {
                    lon: lng,
                    lat: lat,
                    landTableName: landTableName,
                    regionId: that.currentRegionInfo.regionId
                }).then(response => {
                    let result = response.data;
                    let data = result.data;
                    // 弹出POP弹窗
                    that.showLandPopup(data);
                });
            },

            /**
             * 打开地块属性的POPUP弹窗
             */
            showLandPopup(data, toCenter) {
                let that = this;
                // 关闭之前的POPUP
                that.clearLandPopup();
                if (!data) {
                    return;
                }
                // 添加高亮图层
                let wkt = data.geometry;
                that.clickLandLayer = that.$omnivore.wkt.parse(wkt);
                that.clickLandLayer.setStyle({
                    color: "#FF0000",
                    weight: 3,
                    fillColor: "#FF0000",
                    fillOpacity: 0.2,
                });
                that.map.addLayer(that.clickLandLayer);
                // 设置弹窗的HTML
                let rankName = data.rankName;
                let rankId = data.rankId;
                let cropId = data.cropId;
                let cropName = that.cropMap[cropId];
                if (!cropName) {
                    cropName = "一";
                }
                let area = data.area;
                // 标题
                let content = '<div style="width:220px;display: flex;margin-top:10px;">';
                content += '<div style="width:180px;font-weight: bold;font-size: 14px;">' + rankName + '</div>';
                content += '<div style="margin-left:10px;"><a rankId="' + rankId + '"href="javascript:void(0);">详情</a></div>';
                content += '</div>';
                // 作物信息
                content += '<div style="width:220px;display: flex;margin-top:15px;">';
                // 作物类型
                content += '<div style="width:50%;">';
                content += '<div style="font-size:12px;color: #707070;">种植作物</div>';
                content += '<div style="font-size:16px;margin-top:5px;">' + cropName + '</div>';
                content += '</div>';
                // 面积
                content += '<div style="width:50%;">';
                content += '<div style="font-size:12px;color: #707070;">面积（亩）</div>';
                content += '<div style="font-size:16px;margin-top:5px;">' + area + '</div>';
                content += '</div>';
                content += '</div>';
                // 创建新的POPUP
                let lon = data.lon;
                let lat = data.lat;
                let autoPan = true;
                // 定位到该POP,并设置偏移让POP居中显示
                if (toCenter) {
                    let vp = that.map.latLngToLayerPoint([lat, lon]);
                    vp.y = vp.y - 50;
                    let newLatlng = that.map.layerPointToLatLng(vp);
                    that.map.panTo(newLatlng);
                    autoPan = false;
                }
                // 显示POPUP
                that.clickPopup = L.popup(
                    {
                        autoPan: autoPan,
                        minWidth: 200,
                    }
                ).setLatLng([lat, lon]).setContent(content).addTo(that.map);
            },

            /**
             *  获取地块图层名称
             */
            async getLandLayerName(regionInfo) {
                let that = this;
                let url = that.getLandLayerNameUrl;
                let response = await that.$axios.post(url, {
                    dataTime: that.dateYear + "-01-01",
                    regionId: regionInfo.regionId,
                    regionCode: regionInfo.regionCode,
                    level: regionInfo.level
                });
                let result = response.data;
                return result.msg;
            },

            /**
             * 加载并渲染行政区划边界图层
             */
            loadSelectRegionLayer(regionInfo) {
                let that = this;
                let regionId = regionInfo.regionId;
                let regionList = that.cacheRegionList[regionId];
                if (regionList) { // 已在缓存中
                    that.renderRegionList(regionList);
                    // 渲染注记，乡镇区划不渲染注记
                    if (regionInfo.level < 5) {
                        that.renderRegionLabelList(regionList.features);
                    }
                    return;
                }
                // 如果行政区划级别为乡镇则只获取该乡镇的边界
                let cqlFilter = "parent_id=" + regionId;
                if (regionInfo.level >= 5) {
                    cqlFilter = "region_code='" + regionInfo.regionCode + "'";
                }
                // 不在缓存中则请求GEOSERVER获取边界
                that.$axios.get(that.lyrRegionLayerUrl, {
                    params: {
                        service: "WFS",
                        request: "GetFeature",
                        typeName: "ly_dev_china",
                        outputFormat: "application/json",
                        CQL_FILTER: cqlFilter
                    }
                }).then(function (response) {
                    let result = response.data;
                    // 放入缓存中
                    that.cacheRegionList[regionId] = result;
                    // 渲染边界
                    that.renderRegionList(result);
                    // 渲染注记，乡镇区划不渲染注记
                    if (regionInfo.level < 5) {
                        that.renderRegionLabelList(result.features);
                    }
                });
            },

            /**
             * 渲染行政区划边界
             */
            renderRegionList(result) {
                let that = this;
                that.regionLayerGroup = L.geoJSON(result, {
                    style: function (feature) {
                        return {
                            color: "#FFF",
                            weight: 2,
                            fill: false,
                        };
                    },
                    markersInheritOptions: true
                });
                that.map.addLayer(that.regionLayerGroup);
            },

            /**
             *  添加行政区划边界注记
             */
            renderRegionLabelList(features) {
                let that = this;
                that.regionLayerLabelGroup = new L.layerGroup().addTo(that.map);
                features.forEach(feature => {
                    let prop = feature.properties;
                    let chinaName = prop.china_name;
                    let centeroid = prop.centroid; //中心点
                    let coordinates = centeroid.coordinates;  //中心点坐标
                    let lnglat = [coordinates[1], coordinates[0]];
                    let bgColor, fontColor;
                    bgColor = '#007DFF';
                    fontColor = '#FFF';
                    let html = '<div style="background: ' + bgColor + ';color: ' + fontColor + ';border-radius:16px;text-align: center;">';
                    html += '<div style="font-size:14px;padding:3px 3px;">' + chinaName + '</div>';
                    html += '</div>';
                    let labelSize = chinaName.length * 17 + 6;
                    let lIcon = new L.divIcon({
                        html: html,
                        className: 'my-div-icon',
                        iconSize: [labelSize, 32]
                    });
                    let lMarker = new L.marker(lnglat, {
                        icon: lIcon
                    });
                    // 设置行政区划编码
                    let featureId = feature.id;
                    featureId = featureId.replace("ly_dev_china.", "");
                    feature.regionId = featureId;
                    lMarker.regionId = featureId;
                    // 添加注记
                    that.regionLayerLabelGroup.addLayer(lMarker);
                    // 点击缩放到对应行政区划
                    lMarker.on("click", function (evt) {
                        let _marker = evt.target;
                        JHGEvent.trigger("Region/SelectRegion", _marker.regionId);
                    });
                });
            },

            /**
             * WKT转换点
             */
            wktToLatlng(wkt) {
                let startIdx = wkt.indexOf("(");
                let endIdx = wkt.indexOf(")");
                let wktStr = wkt.substring(startIdx + 1, endIdx - 1);
                let wktArr = wktStr.split(" ");
                return [wktArr[1], wktArr[0]];
            },

            /**
             *  构造地块请求过滤参数
             */
            getFilterParam(regionId, cropId) {
                let filter = '<Filter><And>';
                if (regionId) {
                    filter += '<PropertyIsEqualTo><PropertyName>parent_id</PropertyName><Literal>' + regionId + '</Literal></PropertyIsEqualTo>';
                }
                if (cropId) {
                    filter += '<PropertyIsEqualTo><PropertyName>crop_id</PropertyName><Literal>' + cropId + '</Literal></PropertyIsEqualTo>';
                }
                filter += '</And></Filter>';
                return filter;
            },

            /**
             * 清理地图图层
             */
            clearMap() {
                let that = this;
                // 清除行政区划边界图层
                if (that.regionLayerGroup) {
                    that.regionLayerGroup.clearLayers();
                    that.map.removeLayer(that.regionLayerGroup);
                    that.regionLayerGroup = null;
                }
                // 清除行政区划边界注记图层
                if (that.regionLayerLabelGroup) {
                    that.regionLayerLabelGroup.clearLayers();
                    that.map.removeLayer(that.regionLayerLabelGroup);
                    that.regionLayerLabelGroup = null;
                }
                // 清除地块图层
                if (that.landLayer) {
                    that.map.removeLayer(that.landLayer);
                    that.landLayer = null;
                }
                // 注销地图点击事件
                that.map.off("click", that.landClick);
                // 关闭POPUP弹窗
                that.clearLandPopup();
            },

            /**
             * 关闭POPUP弹窗
             */
            clearLandPopup() {
                let that = this;
                // 关闭点击选取的图层
                if (that.clickLandLayer) {
                    that.map.removeLayer(that.clickLandLayer);
                    that.clickLandLayer = null;
                }
                // 关闭POPUP弹窗
                if (that.clickPopup) {
                    that.map.removeLayer(that.clickPopup);
                    that.clickPopup = null;
                }
            },

            /**
             * 缩小地图
             */
            zoomIn() {
                this.map.zoomIn();
            },

            /**
             * 放大地图
             */
            zoomOut() {
                this.map.zoomOut();
            },

            /**
             * 重置位置
             */
            resetMap() {
                let that = this;
                that.map.setView(that.mapOptions.center, that.mapOptions.center, {
                    animate: true
                });
            }
        }
    };
</script>

<style lang="less">
    .bgcolor007dff {
        background-color: #007dff;
    }

    .bgcolorffffff {
        background-color: #ffffff;
    }

    .map-container {
        position: absolute;
        top: 0;
        left: 400px;
        width: calc(100% - 400px);
        height: 100%;
        z-index: 100;

        .toolBar {
            position: absolute;
            top: 20px;
            right: 20px;
            z-index: 1000;

            .tool-item {
                display: inline-flex;
                justify-content: space-between;
                align-items: center;
                width: 108px;
                height: 34px;
                padding: 0 10px;

                &:last-child {
                    margin-left: 10px;
                    color: #ffffff;
                }

                i {
                    display: block;
                    width: 22px;
                    height: 22px;

                    &.icon-square {
                        background: url("../assets/images/index/square.png") no-repeat;
                        background-size: 22px 22px;
                    }

                    &.icon-load {
                        background: url("../assets/images/index/download.png") no-repeat;
                        background-size: 22px 22px;
                    }
                }
            }
        }

        .controlBar {
            position: absolute;
            top: 60px;
            right: 20px;
            box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16);
            z-index: 1000;

            .tool-item {
                display: flex;
                justify-content: center;
                align-items: center;
                width: 30px;
                height: 30px;
                background: #ffffff;

                i {
                    font-size: 16px;
                    color: #858585;
                }

                &:hover {
                    i {
                        color: #007dff;
                    }
                }
            }
        }
    }
</style>
