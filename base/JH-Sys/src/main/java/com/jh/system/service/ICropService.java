package com.jh.system.service;


import com.jh.vo.ResultMessage;

/**
 * 查询作物
 * @version <1> 2017-12-06 cxw : Created.
 */
public interface ICropService {

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2017-12-06 cxw : Created.
     */
    ResultMessage findCropList(Long regionId);
}
