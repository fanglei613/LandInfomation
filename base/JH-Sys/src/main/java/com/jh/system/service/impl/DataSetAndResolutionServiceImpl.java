package com.jh.system.service.impl;

import com.jh.system.entity.RelateDataSetResolution;
import com.jh.system.mapping.IDataSetAndResolutionMapper;
import com.jh.system.mapping.IDictMapper;
import com.jh.system.service.IDataSetAndResolutionService;
import com.jh.util.StringUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 数据集和精度关联Service
 * 1.
 *
 * @version <1> 2018/8/30 16:03 zhangshen: Created.
 */
@Service
@Transactional
public class DataSetAndResolutionServiceImpl implements IDataSetAndResolutionService {

    @Autowired
    private IDataSetAndResolutionMapper dataSetAndResolutionMapper;

    @Autowired
    private IDictMapper dictMapper;

    @Override
    public ResultMessage queryResolutionListByDataSetId(Integer dataSetId) {
        if (null == dataSetId) {
            return ResultMessage.fail("数据集不能为空");
        }
        return ResultMessage.success(dataSetAndResolutionMapper.queryResolutionListByDataSetId(dataSetId));
    }

    /**
     * 根据数据集id查询绑定的精度信息
     * @param relateDataSetResolution
     * @return
     */
    @Override
    public ResultMessage queryResolutionListByDSId(RelateDataSetResolution relateDataSetResolution) {
        if (null == relateDataSetResolution) {
            return ResultMessage.fail("数据集不能为空");
        }
        return ResultMessage.success(dictMapper.findAllResolutionByDatasetId(relateDataSetResolution));
    }


    /**
     * 新增或修改数据集与精度的绑定
     * @param relateDataSetResolution
     * @return
     */
    public ResultMessage addDatasetAndResolutionRelateData(RelateDataSetResolution relateDataSetResolution){
        ResultMessage result = ResultMessage.success();

        if(null != relateDataSetResolution.getDataSetId()){
            int num = dataSetAndResolutionMapper.delDatasetAndResolutionRelateData(relateDataSetResolution.getDataSetId());
        }

        List<RelateDataSetResolution> dataSetResolutionList = new ArrayList<RelateDataSetResolution>();
        for(Integer resolution : relateDataSetResolution.getResolutionIds()){
            RelateDataSetResolution rds = new RelateDataSetResolution();
            rds.setDataSetId(relateDataSetResolution.getDataSetId());
            rds.setResolutionId(resolution);
            dataSetResolutionList.add(rds);
        }
        dataSetAndResolutionMapper.addDatasetAndResolutionRelateData(dataSetResolutionList);

        return result;
    }

    /**
     * Description: 查询数据集精度列表
     * @param
     * @return 数据精度列表
     * @version <1> 2019-03-13  cxw: Created.
     */
    public ResultMessage findDsResolutionList(){
        ResultMessage result = ResultMessage.success();
        List<RelateDataSetResolution> list =  new ArrayList<RelateDataSetResolution>();
        list = dataSetAndResolutionMapper.findDsResolutionList();
        if(list!=null)
        {
            result.setData(list);
        }
        else{
            result =  ResultMessage.fail("数据集不能为空");
        }
        return result;
    }

}
