package com.jh.system.service.impl;

import com.jh.constant.SysConstant;
import com.jh.system.mapping.ICropMapper;
import com.jh.system.service.ICropService;
import com.jh.vo.ResultMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 查询作物
 * @version <1> 2017-12-06 cxw : Created.
 */
@Service
public class CropService implements ICropService {

    private static Logger logger = Logger.getLogger(CropService.class);

    @Autowired
    private ICropMapper cropMapper;

    /**
     * 根据区域加载作物
     * @param regionId : 区域ID
     * @return
     * @version <1> 2017-12-06 cxw : Created.
     */
    @Override
    public ResultMessage findCropList(Long regionId) {
        ResultMessage result = null;
        if(regionId!=null){
            List<Map<String,Object>> dsList = cropMapper.findCropList(regionId);
            result = ResultMessage.success(dsList);
        }else{
            logger.error("请求参数错误");
            result = ResultMessage.fail(SysConstant.Request_Param_Empty);
            result.setMsg("区域不能为空.");
        }
        return result;
    }
}
