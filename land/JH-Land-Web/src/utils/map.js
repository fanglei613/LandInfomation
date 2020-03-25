/*
 * @Descripttion: leaflet
 * @version: 1.1
 * @Author: yangkai
 * @Date: 2020-03-02 21:04:30
 * @LastEditors: yangkai
 * @LastEditTime: 2020-03-16 15:06:39
 */
import "leaflet/dist/leaflet.css";
import $L from "leaflet";

// 解决leaflet 默认marker无法显示的问题
import icon from "leaflet/dist/images/marker-icon.png";
import iconShadow from "leaflet/dist/images/marker-shadow.png";
let defaultIcon = $L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow
});
$L.Marker.prototype.options.icon = defaultIcon;

const createMap = (divId, options) => {
  let map = $L.map(divId, options);
  return map;
};

const createTileLayer = async (map, url, options) => {
  let tileLayer = await $L.tileLayer(url, options);
  tileLayer.addTo(map);
  return tileLayer;
};

const createIcon = options => {
  return $L.icon(options);
};

const createMarkerByXY = (map, coordinate, options = {}) => {
  let marker = $L.marker($L.latLng(coordinate[1], coordinate[0]), options);
  marker.addTo(map);
  return marker;
};

const createPloyline = (map, linePath, lineOpts) => {
  const ployLine = $L.ployline(linePath, lineOpts);
  ployLine.addTo(map);
  return ployLine;
};

const setZoomControlPos = (map, opts, pos) => {
  let zoom = $L.control.zoom(opts, pos);
  zoom.addTo(map);
  zoom.setPosition(pos);
  return zoom;
};

export default {
  createMap,
  createTileLayer,
  createIcon,
  createMarkerByXY,
  createPloyline,
  setZoomControlPos
};
