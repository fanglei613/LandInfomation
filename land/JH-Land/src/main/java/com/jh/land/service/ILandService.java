package com.jh.land.service;

import com.github.pagehelper.PageInfo;
import com.jh.land.entity.LandEntity;
import com.jh.land.model.LandParam;
import com.jh.vo.ResultMessage;

import javax.xml.transform.Result;

/**
 * 地块服务
 */
public interface ILandService {

    /**
     * 获取乡镇地块统计信息
     */
    ResultMessage queryLandStatistics(LandParam landParam);

    /**
     * 分页查询地块
     */
    PageInfo<LandEntity> queryLandList(LandParam landParam);

    /**
     * 根据点击的经纬度获取地块
     */
    ResultMessage queryLandInfoByLonLat(LandParam landParam);

    /**
     * 根据ID获取地块
     */
    ResultMessage queryLandInfoById(LandParam landParam);

}
