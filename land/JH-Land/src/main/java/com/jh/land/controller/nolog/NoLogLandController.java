package com.jh.land.controller.nolog;

import com.github.pagehelper.PageInfo;
import com.jh.land.entity.LandEntity;
import com.jh.land.model.LandParam;
import com.jh.land.service.ILandService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nolog/land")
public class NoLogLandController {

    @Autowired
    private ILandService landService;

    /**
     * 获取乡镇地块列表
     */
    @PostMapping("/findLandStatistics")
    public ResultMessage findLandStatistics(@RequestBody LandParam landParam) {
        return landService.queryLandStatistics(landParam);
    }

    /**
     * 获取乡镇地块列表
     */
    @PostMapping("/findLandList")
    public PageInfo<LandEntity> findLandList(@RequestBody LandParam landParam) {
        return this.landService.queryLandList(landParam);
    }

    /**
     * 根据点击的经纬度获取地块
     */
    @PostMapping("/findLandInfoByLonLat")
    public ResultMessage findLandInfoByLonLat(@RequestBody LandParam landParam) {
        return this.landService.queryLandInfoByLonLat(landParam);
    }

    /**
     * 根据ID获取地块
     */
    @PostMapping("/findLandInfoById")
    public ResultMessage findLandInfoById(@RequestBody LandParam landParam) {
        return this.landService.queryLandInfoById(landParam);
    }

}
