package com.jh.system.service.impl;

import com.jh.system.entity.RelateCropRegion;
import com.jh.system.mapping.IDictMapper;
import com.jh.system.mapping.IRegionAndCropMapper;
import com.jh.system.mapping.IRegionMapper;
import com.jh.system.model.DictDataReturn;
import com.jh.system.model.RegionCropParam;
import com.jh.system.service.IRegionAndCropService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 区域与作物配置接口
 * @version <1> 2018-1-25 cxw: Created.
 */
@Transactional
@Service
public class RegionAndCropServiceImpl implements IRegionAndCropService {

    @Autowired
    private IDictMapper dictMapper;

    @Autowired
    private IRegionMapper regionMapper;

    @Autowired
    private IRegionAndCropMapper regionAndCropMapper;

    /**
     * 根据区域查询作物
     * 在配置区域与作物时显示某一区域对应的所有作物，且该区域已分配的作物复选框勾选
     * regionId 区域ID
     * <1> 2018-1-25 cxw: Created.
      <2> 2018-8-7 wl: update.
     */
    @Override
    public ResultMessage findCropByRegionId(RelateCropRegion relateCropRegion) {
        List<DictDataReturn>  ddrs = new ArrayList<DictDataReturn>();
        ResultMessage result = ResultMessage.success();
        try {
            if(relateCropRegion!=null)
            {
                if(relateCropRegion.getParentIds()!=null&&relateCropRegion.getRegionId()!=null) {
                    ddrs = dictMapper.findAllCropByRegionId(relateCropRegion);
                    result = ResultMessage.success(ddrs);
                }
                else{
                    result= ResultMessage.fail();
                    result.setMsg("参数不能为空");
                }
            }
            else{
                result= ResultMessage.fail();
                result.setMsg("参数不能为空");
            }

        }catch (Exception e){
            return result;
        }

        return result;
    }

    /**
     *批量新增数据集与分辨率关联
     * regionId 区域ID
     * cropId 作物ID
     * <1> 2018-1-24 cxw: Created.
     * * <2> 2018-8-7 wl: update.
     */
    @Override
    public ResultMessage addRegionAndCropRelateData(RegionCropParam regionCropParam) {
        ResultMessage result = ResultMessage.success();
        if(regionCropParam.getCropsId().size()>0 && regionCropParam.getRegionIds().size()>0 && regionCropParam.getRegionCodes().size()>0){
            List<Long> regionIds = new ArrayList<>();//组装regionIds  如果需要应用到下级 首先查询所有下级区域信息  如果不需要则为选中的regionIds
            if(regionCropParam.getYesOrNot()==1){//应用于下级
                Set<Long> regionIdSet = new HashSet<>();//去除重复的regionId
                for(int i = 0 ;i<regionCropParam.getRegionCodes().size();i++){
                    List<Long> regions = regionMapper.findRegionsByCode(regionCropParam.getRegionCodes().get(i));//查询所有的下级区域id
                    regionIdSet.addAll(regions);
                }
                regionIds.addAll(regionIdSet);
            }else{
                regionIds = regionCropParam.getRegionIds();
            }
            //先根据id批量删除
            int num = regionAndCropMapper.delRegionAndCropRelateData(regionIds);
            List<RegionCropParam> regionCropParamList=new ArrayList<>();

            for(int i=0;i<regionCropParam.getCropsId().size();i++){
                for (int j = 0 ; j < regionIds.size();j++){//设置区域
                    RegionCropParam newRegionCrop = new RegionCropParam();
                    newRegionCrop.setCropId(regionCropParam.getCropsId().get(i));//设置作物
                    newRegionCrop.setRegionId(regionIds.get(j));
                    regionCropParamList.add(newRegionCrop);
                }
            }
            //然后做批量插入的操作
            int insetSize = regionCropParamList.size();
            int i = 0;
            while (insetSize > 2000){//一次最多插入2000条数据
                regionAndCropMapper.addRegionAndCropRelateData(regionCropParamList.subList(i,i+2000));
                i = i +2000;
                insetSize = insetSize -2000;
            }
            if(insetSize > 0){
                regionAndCropMapper.addRegionAndCropRelateData(regionCropParamList.subList(i,i+insetSize));
            }
        }else{
            return  ResultMessage.fail();
        }

        return result;
    }
}

