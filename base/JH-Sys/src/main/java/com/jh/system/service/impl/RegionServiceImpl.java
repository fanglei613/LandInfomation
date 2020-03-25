package com.jh.system.service.impl;

import com.jh.cache.service.ICacheService;
import com.jh.constant.CommonDefineEnum;
import com.jh.constant.ConstantUtil;
import com.jh.constant.SysConstant;
import com.jh.system.Enum.RegionEnum;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.system.entity.InitRegion;
import com.jh.system.entity.PermAccount;
import com.jh.system.mapping.IRegionMapper;
import com.jh.system.model.RegionAppReturn;
import com.jh.system.model.RegionParam;
import com.jh.system.model.RegionTreeReturn;
import com.jh.system.service.IRegionService;
import com.jh.util.AccountTokenUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class RegionServiceImpl extends BaseServiceImpl<RegionParam, InitRegion, Long> implements IRegionService {
    @Autowired
    private IRegionMapper regionMapper;
    @Autowired
    private ICacheService cacheService;

    private Logger logger = Logger.getLogger(RegionServiceImpl.class);
    
    public ResultMessage findRegionListByParentId(Long parentId) {
        List<RegionParam> regionList = regionMapper.findRegionListByParentId(parentId);
        return ResultMessage.success(regionList);
    }

    public ResultMessage findBlockRegionListByParentId(Long parentId,Integer level) {
        List<RegionParam> regionList = regionMapper.findBlockRegionListByParentId(parentId,level);
        return ResultMessage.success(regionList);
    }

    /**
     *  @description: 根据父类区域ID查询其省级区域列表
     *  @param parentId: 上一级区域ID
     *  @version <1> 2018-04-17 cxw:Created.
     */
    @Override
    public ResultMessage findRegionListByParentIdForReport(Long parentId) {
        List<InitRegion> regionList = regionMapper.findRegionListByParentIdForReport(parentId);
        return ResultMessage.success(regionList);
    }

    @Override
    protected IBaseMapper<RegionParam, InitRegion, Long> getDao() {
        return regionMapper;
    }

    /**
     * @description: 查询区域数据
     * @param regionId
     * @return
     * @version <1> 2018/1/26 djh： Created.
     */
    public ResultMessage queryInitRegionByInitRegionId(Long regionId) {
        if (regionId == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }
        InitRegion initRegion1 = regionMapper.getById(regionId);
        if (initRegion1 == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
        }
        return ResultMessage.success(initRegion1);
    }

    /**
     * @description: 更新区域数据
     * @param request request请求对象
     * @param initRegion 区域对象
     * @return
     * @version <1> 2018/1/26 djh： Created.
     */
    public ResultMessage updateInitRegionByInitRegionId(HttpServletRequest request, InitRegion initRegion) {
        if (initRegion == null || initRegion.getRegionId() == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }

        try {
            PermAccount permAccount = (PermAccount) request.getSession().getAttribute(ConstantUtil.CURRENT_PERMACCOUNT);
            if (null != permAccount) {
                initRegion.setModifierName(permAccount.getNickName());
                initRegion.setModifier(permAccount.getAccountId());
            }
            initRegion.setModifyTime(LocalDateTime.now());
            regionMapper.updateByPrimaryKeySelective(initRegion);
            //更新区域缓存
            cacheService.updateCacheRegion(initRegion,false);
            return ResultMessage.success(CommonDefineEnum.RECORD_UPDATE_SUCCESS.getValue(), CommonDefineEnum.RECORD_UPDATE_SUCCESS.getMesasge());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
        }
    }

    /**
     * @description: 保存区域对象
     * @param request request请求对象
     * @param initRegion 区域对象
     * @version <1> 2018/1/26 djh： Created.
     */
    public synchronized ResultMessage saveInitRegion(HttpServletRequest request, InitRegion initRegion) {
        if (initRegion == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }

        setInitRegionId(initRegion);
        try {
            PermAccount permAccount = (PermAccount) request.getSession().getAttribute(ConstantUtil.CURRENT_PERMACCOUNT);
            if (null != permAccount) {
                initRegion.setCreatorName(permAccount.getNickName());
                initRegion.setCreator(permAccount.getAccountId());
            }
            initRegion.setCreateTime(LocalDateTime.now());
            regionMapper.save(initRegion);
            return ResultMessage.success(CommonDefineEnum.SAVE_OBJECT_SUCCESS.getValue(), CommonDefineEnum.SAVE_OBJECT_SUCCESS.getMesasge());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail(CommonDefineEnum.SAVE_OBJECT_FAIL.getValue(), CommonDefineEnum.SAVE_OBJECT_FAIL.getMesasge());
        }
    }

    /**
     * 设置区域主键
     *
     * @param initRegion
     * @version <1> 2018-02-10 djh：  Created.
     */
    private void setInitRegionId(InitRegion initRegion) {
        Long id = regionMapper.findMaxSubInitRegionIdByParentId(initRegion.getParentId());
        if (id == null || id == 0) {
            String tid = initRegion.getParentId().toString();
            int len = tid.length();
            String first = tid.substring(0, len - 8);
            String ji = tid.substring(len - 8, len - 6);
            if (ji.startsWith("0")) {
                int cnt = Integer.parseInt(ji.substring(1)) + 1;
                if (cnt < 10) {
                    ji = "0" + cnt;
                }
                ji += "000001";
            } else {
                ji = (Integer.parseInt(ji) + 1) + "000001";
            }
            initRegion.setRegionId(Long.parseLong(first + ji));
        } else {
            initRegion.setRegionId(id + 1);
        }
    }

    /**
     * @description: 根据区域主键值，删除对应的区域信息
     * @param request request请求对象
     * @param initRegion 区域对象
     * @version <1> 2018/1/26 djh： Created.
     */
    public ResultMessage deleteInitRegionByInitRegionId(HttpServletRequest request, InitRegion initRegion) {
        if (initRegion == null || initRegion.getRegionId() == null) {
            return ResultMessage.fail(CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_PARAMETER_NOT_EXISTS.getMesasge());
        }

        try {
            PermAccount permAccount = (PermAccount) request.getSession().getAttribute(ConstantUtil.CURRENT_PERMACCOUNT);
            if (null != permAccount) {
                initRegion.setModifyTime(LocalDateTime.now());
                initRegion.setModifierName(permAccount.getNickName());
                initRegion.setModifier(permAccount.getAccountId());
            }
            initRegion.setDelFlag(ConstantUtil.SET_DEL_FLAG_IS_DELETE_STATE);
            regionMapper.updateByPrimaryKeySelective(initRegion);
            return ResultMessage.success(CommonDefineEnum.RECORD_UPDATE_SUCCESS.getValue(), CommonDefineEnum.RECORD_UPDATE_SUCCESS.getMesasge());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail(CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getValue(), CommonDefineEnum.FIND_OBJECT_NOT_EXISTS.getMesasge());
        }
    }

    public ResultMessage queryFirstLevelInitRegionList() {
        try {
            List<RegionTreeReturn> regionList= regionMapper.findFirstLevelInitRegionList();
            if (regionList != null) {
                return ResultMessage.success(regionList);
            }
            return ResultMessage.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }

    /**
     * 根据parentID查询子区域信息
     * @param regionParentId 父区域的主键id
     * @return ResultMessage
     * @version<2>  2018-02-06 lcw : updated.
     *  修改该方法的返回类型为ResultMessage
     */
    public ResultMessage querySubRegionListByParentId(Long regionParentId) {
        try {
            List<RegionTreeReturn> regionTreeReturnList = regionMapper.querySubInitRegionListByParentId(regionParentId);
            for (RegionTreeReturn rtr : regionTreeReturnList) {
                List<RegionTreeReturn> regionTreeReturnList2 = regionMapper.querySubInitRegionListByParentId(rtr.getId());
                if (regionTreeReturnList2.size() > 0) {
                    rtr.setParent(true);
                }
            }
            return ResultMessage.success(regionTreeReturnList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage checkRegionCodeIsExists(String regionCode) {
        Integer cnt = regionMapper.checkRegionCodeIsExists(regionCode);
        if (cnt != null && cnt > 0) {
            return ResultMessage.success("1", "查询区域编码存在");
        }
        return ResultMessage.success("0", "查询区域编码不存在");
    }

    @Override
    public ResultMessage findAll() {
        List<InitRegion> regionList=regionMapper.findAll();
        return ResultMessage.success(regionList);
    }



    @Override
    public ResultMessage findAllParentId() {
        List<Long> regionList=regionMapper.findAllParentId();
        return ResultMessage.success(regionList);
    }

    @Override
    public ResultMessage findRegionByChinaName(String chinaName) {
        List<InitRegion> regionList =  regionMapper.findRegionByChinaName("%"+chinaName+"%");
        return ResultMessage.success(RegionEnum.QUERY_REGION_SUCCESS.getKey(), RegionEnum.QUERY_REGION_SUCCESS.getMessage(),regionList);
    }

    /**
     * 根据上一级ID获取区域列表
     * @param parentId: 上一级区域ID
     * @return ResultMessage :
     * @version <1> 2017-11-07 Hayden:Created.
     */
    public List<InitRegion> findRegionsByParentId(Long parentId){
        List<InitRegion> regionList = null;
        regionList = regionMapper.findRegionsByParentId(parentId);

        return regionList;
    }

    /**
     * 根据账号获取区域权限信息
     * 1. 获取账号购买的数据服务，从而得到区域信息
     * 2. 过滤重复区域
     * 3. 获取每个区域的上一级区域，直到区域没有上一级为止。
     * 4. 将账号的权限区域及相关的所有上一级区域进行返回
     * @param accountId: 账号ID
     * @return ResultMessage : 区域列表，如果返回true,包含List<RegionParam>
     * @version <1> 2017-12-20 Hayden:Created.
     */
    @Override
    public ResultMessage findRegionListByAccount(Integer accountId, Long parentId) {
        ResultMessage result = null;
        Set<InitRegion> minRegionSet = new HashSet<InitRegion>();

        if(accountId!=null){
            //获取用户权限区域列表
            List<InitRegion> regionList = regionMapper.findRegionListByAccount(accountId);

            //获取权限区域列表中最小的级别。
            int  minLevel = 9999;
            for(InitRegion region : regionList){
                //CHN-HU-JINGZHOU
                //USA-JA-XXX
                Integer level = region.getLevel();
                if(level < minLevel){
                    minLevel = level ;
                }
            }

            //如果上一级区域ID为空，则表示用户请求权限区域列表的最上层区域数据；否则为权限区域的指定上一级
            int requestLevel = minLevel;
            String parentRegionCode = null;
            if(parentId!=null){
                InitRegion parentRegionParam = regionMapper.findRegionById(parentId);
                requestLevel = (parentRegionParam.getLevel().intValue()+1);
                parentRegionCode = parentRegionParam.getRegionCode();
                // System.out.println("requestLevel="+requestLevel);
            }

            //获取每个权限区域中的最小级别区域
            for(InitRegion region : regionList){
                // System.out.println(region);
                String regionCode = region.getRegionCode();
                int level = region.getLevel().intValue();
                //如果权限区域的级别小于请求级别，则没有下一级
                // System.out.println("level = "+level);
                // System.out.println(level < requestLevel);
                if(level < requestLevel){
                    continue;
                }

                if(!StringUtils.isBlank(parentRegionCode) && regionCode.indexOf(parentRegionCode)==-1){
                    continue;
                }

                String[] regionCodeArray = regionCode.split("-");
                String minRegionCode = null;
                for(int i=0;i<requestLevel;i++){
                    if(StringUtils.isBlank(minRegionCode)){
                        minRegionCode =regionCodeArray[i];
                    }else{
                        minRegionCode +="-"+regionCodeArray[i];
                    }
                }
                // System.out.println("minRegionCode="+minRegionCode);
                InitRegion minRegion = regionMapper.findRegionByCode(minRegionCode);
                minRegionSet.add(minRegion);
            }
            result = ResultMessage.success(minRegionSet);
        }else{
            result = ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        return result;
    }

    @Override
    public ResultMessage findRegionByToken(String accountToken, Long parentId) {
        boolean bool = AccountTokenUtil.isSuperAdmin(accountToken); //判断是否为超级管理员
        if(bool){ //超级管理员，查询所有区域
            return ResultMessage.success(findRegionsByParentId(parentId));
        }else{
            Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(accountToken);
            logger.info("{findRegionByToken}userInfo："+userInfo+"");
            if(userInfo==null){
            	return ResultMessage.fail(SysConstant.Param_Login_Token);
            }
            Integer accountId = Integer.valueOf(userInfo.get("accountId").toString());
            return findRegionListByAccount(accountId,parentId);
        }
    }


    @Override
    public ResultMessage findAllRegionByToken(String accountToken, Long parentId) {
            return ResultMessage.success(findRegionsByParentId(parentId));
    }

    @Override
    public List<Map<String,Object>> findRegionByParentId(Map<String,Object> map) {
        List<Map<String,Object>> list = regionMapper.findRegion(map);
        return list;
    }

    @Override
    public ResultMessage findRegionIdListByRegionCode(String regionCode) {
        List<Long> list = regionMapper.findRegionIdListByRegionCode(regionCode);
        return ResultMessage.success(list);
    }

    /**
     * region_code编码：
     * 规则：1.取所有汉字拼音的前三位
     *      2.若重复，则第三位随机生成一个
     */
    @Override
    public ResultMessage encodeRegionCode(Integer level){
        List<InitRegion> list=regionMapper.findNullRegionCodeList(level);
        for(InitRegion region:list){
            String newRegionCode="";
            InitRegion parentRegion = regionMapper.getByRegionId(region.getParentId());
           String pcode= parentRegion.getRegionCode();
           String chinaName=region.getChinaName();
            if(StringUtils.isNotBlank(chinaName)){
                String str="AAA";//PinyinUtils.toHanyuPinyinUp(chinaName);
                if(StringUtils.isNotBlank(str)&&str.length()>=3){
                    newRegionCode=getUniqueCode(str.substring(0,3),pcode);
                    region.setRegionCode(newRegionCode);
                    regionMapper.updateCodeById(region);
                }
            }
        }
        return ResultMessage.success();
    }

    /**
     * regin_name编码
     * 规则：1、首汉字拼音+第二个汉字拼音，首字母大写
     */
    @Override
    public ResultMessage encodeRegionName(Integer level){
        List<InitRegion> list=regionMapper.findNullRegionCodeList(level);
        for(InitRegion region:list){
            String chinaName=region.getChinaName();
            if(StringUtils.isNotBlank(chinaName)){
                String newRegionName="aaa";//PinyinUtils.getFirstLetterString(chinaName.substring(0,2));
                region.setRegionName(newRegionName);
                regionMapper.updateCodeById(region);
            }
        }
        return ResultMessage.success();
    }

    @Override
    public ResultMessage findRegion(Map<String, Object> map) {
        List<Map<String,Object>> list= regionMapper.findRegion(map);
        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage findRegionFamily(Long regionId) {

        Map<String,Object> familyMap = new HashMap<String, Object>();
        ResultMessage regionMessage =  this.getById(regionId);
        if(regionMessage.isFlag()){

            InitRegion region = (InitRegion)regionMessage.getData();
            familyMap.put("regionId",region.getRegionId());
            familyMap.put("regionCode",region.getRegionCode());
            familyMap.put("level",region.getLevel());
            familyMap.put("centroid", region.getCentroid());

            if(region.getLevel() == 2){//省
                familyMap.put("provinceId", region.getRegionId());
                familyMap.put("capitalId", region.getCapitalId());
            }else if(region.getLevel() == 3){ //市州
                familyMap.put("provinceId",region.getParentId());
                familyMap.put("cityId",region.getRegionId());
            }else if(region.getLevel() == 4){ //区县
                Long cityId = region.getParentId();
                familyMap.put("cityId", cityId);
                ResultMessage cityRegionMsg = this.getById(cityId);
                if(cityRegionMsg.isFlag()){
                    InitRegion cityRegion = (InitRegion)cityRegionMsg.getData();
                    familyMap.put("provinceId", cityRegion.getParentId());
                }
            }
        }
        return ResultMessage.success(familyMap);
    }

    private String getUniqueCode(String code,String pcode){
        String newRegionCode=pcode+"-"+code;
        //校验是否重复
        int count= regionMapper.checkCodeIsExists(newRegionCode);
        if(count>0){
            code=code.substring(0,2)+"A";//PinyinUtils.getRamdomChar();
            newRegionCode=getUniqueCode(code,pcode);
        }
        return newRegionCode;
    }

    /**
     * 根据所有的省级直管县或者市
     * @return
     */
    @Override
    public ResultMessage findMunicipalityArea() {
        List<Long> regionList = regionMapper.findMunicipalityArea();
        return ResultMessage.success(regionList);
    }

    /**
     * 根据区域id查询此区域下所有子孙元素,app用
     * @param
     * @return
     * @version <1> 2019/4/13 15:46 zhangshen:Created.
     */
    @Override
    public ResultMessage queryRegionAppReturn(Long regionId) {
        List<InitRegion> list = regionMapper.findRegionsByParentId(regionId);
        List<RegionAppReturn> regionAppReturnList = new ArrayList<RegionAppReturn>();
        for (InitRegion initRegion : list) {
            RegionAppReturn obj = new RegionAppReturn();
            obj.setValue(initRegion.getRegionId());
            obj.setText(initRegion.getChinaName());
            List<InitRegion> list2 = regionMapper.findRegionsByParentId(initRegion.getRegionId());
            if (list2.size() > 0) {
                obj.setParent(true);
                obj.setChildren((List<RegionAppReturn>) queryRegionAppReturn(initRegion.getRegionId()).getData());
            } else {
                obj.setParent(false);
            }
            regionAppReturnList.add(obj);
        }
        return ResultMessage.success(regionAppReturnList);
    }

    @Override
    public ResultMessage findRegionByCode(String regionCode) {
        InitRegion minRegion = regionMapper.findRegionByCode(regionCode);
        return ResultMessage.success(minRegion);
    }

}
