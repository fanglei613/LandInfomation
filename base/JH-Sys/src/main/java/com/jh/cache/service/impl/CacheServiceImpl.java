package com.jh.cache.service.impl;

import com.jh.enums.CacheDataEnum;
import com.jh.enums.CacheTypeEnum;
import com.jh.system.entity.Dict;
import com.jh.system.entity.InitRegion;
import com.jh.system.entity.PermAccount;
import com.jh.system.model.DictParam;
import com.jh.cache.service.ICacheService;
import com.jh.system.model.PersonParam;
import com.jh.system.service.IDictService;
import com.jh.system.service.IPermAccountService;
import com.jh.system.service.IRegionService;
import com.jh.util.*;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.netflix.config.DeploymentContext.ContextKey.region;


@Service
@Transactional
public class CacheServiceImpl  implements ICacheService {

    public static final Logger logger= LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private IDictService dictService;

    @Autowired
    private IPermAccountService permAccountService;

    @Autowired
    private IRegionService regionService;

    /**
     * 缓存过期时间 永不过期
     */
    Integer expireSecond=Integer.valueOf(PropertyUtil.getPropertiesForConfig("Cache_Expire"));

    /**
     *根据字典ID查询名称
     * @return dictId
     * @version <1> 2019-02-12 lijie： Created.
     */
    @Override
    public ResultMessage queryNameByDictId(Integer dictId) {
        String dictName=RedisUtil.get(CacheUtil.dict_name_key+dictId);
        if(StringUtils.isBlank(dictName)){
            DictParam dict=new DictParam();
            dict.setDictId(dictId);
            ResultMessage result=dictService.findDictById(dict);
            if(result.isFlag()){
                DictParam d=(DictParam)result.getData();
                dictName=d!=null?d.getDataName():"";
                RedisUtil.set(CacheUtil.dict_name_key+dictId,dictName);
                logger.info("dictId:{},查询数据库，并设置字典名称缓存成功",dictId);
            }else{
                logger.error("数据字典ID{}-不存在对应的名称",dictId);
            }
        }else{
            logger.info("dictId:{},从缓存取值字典名称成功",dictId);
        }
        return ResultMessage.success("查询成功",dictName);
    }

    /**
     *根据字典ID查询编码
     * @return dictId
     * @version <1> 2019-02-12 lijie： Created.
     */
    @Override
    public ResultMessage queryCodeByDictId(Integer dictId) {
        String dictCode=RedisUtil.get(CacheUtil.dict_code_key+dictId);
        if(StringUtils.isBlank(dictCode)){
            DictParam dict=new DictParam();
            dict.setDictId(dictId);
            ResultMessage result=dictService.findDictById(dict);
            if(result.isFlag()){
                DictParam d=(DictParam)result.getData();
                dictCode=d!=null?d.getDataCode():"";
                RedisUtil.set(CacheUtil.dict_code_key+dictId,dictCode);
                logger.info("dictId:{},查询数据库，并设置字典编码缓存成功",dictId);
            }else{
                logger.error("数据字典ID{}-不存在对应的编码",dictId);
            }
        }else{
            logger.info("dictId:{},从缓存获取字典编码成功",dictId);
        }
        return ResultMessage.success("查询成功",dictCode);
    }

    @Override
    public ResultMessage findSubListByDictId(Integer dictId) {
        List<Dict> dictList=new ArrayList<Dict>();
        Map<String,Object> all = RedisUtil.getJsonToMap(CacheUtil.dict_list_key+dictId);
        if(CollectionUtil.isNotEmpty(all)){
            String dictListStr= all.get(CacheUtil.dict_list_key+dictId).toString();
            dictList=JsonUtils.jsonToList(dictListStr,Dict.class);
        }
        if(CollectionUtil.isEmpty(dictList)){
            Dict dict=new Dict();
            dict.setParentId(dictId);
            ResultMessage result=dictService.queryDictByParentId(dict);
            if(result.isFlag()){
                dictList=(List<Dict>)result.getData();
                if(CollectionUtil.isNotEmpty(dictList)){
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put(CacheUtil.dict_list_key+dictId,dictList);
                    RedisUtil.setJsonStr(CacheUtil.dict_list_key+dictId,map,expireSecond);
                    logger.info("dictId:{},查询数据库，并设置缓存成功",dictId);
                }else{
                    logger.error("数据字典Id{}-不存在对应的子集列表",dictId);
                }
            }else{
                logger.error("数据字典Id{}-查询子集列表失败",dictId);
            }
        }else{
            logger.info("dictId:{},从缓存取值字典子集列表成功",dictId);
        }
        return ResultMessage.success(dictList);
    }

    @Override
    public ResultMessage findType(Integer dictId) {
        //查询一级菜单   查询二级菜单

        //首先查询 一级的大类  然后根据大类的id查询小类
        List<Dict> dictList=new ArrayList<Dict>();
        Map<String,Object> all = RedisUtil.getJsonToMap(CacheUtil.dict_list_key+dictId);
        if(CollectionUtil.isNotEmpty(all)){
            String dictListStr= all.get(CacheUtil.dict_list_key+dictId).toString();
            dictList=JsonUtils.jsonToList(dictListStr,Dict.class);
        }
        if(CollectionUtil.isEmpty(dictList)){
            Dict dict=new Dict();
            dict.setParentId(dictId);
            ResultMessage result=dictService.queryDictByParentId(dict);
            if(result.isFlag()){
                dictList=(List<Dict>)result.getData();
                if(CollectionUtil.isNotEmpty(dictList)){
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put(CacheUtil.dict_list_key+dictId,dictList);
                    RedisUtil.setJsonStr(CacheUtil.dict_list_key+dictId,map,expireSecond);
                    logger.info("dictId:{},查询数据库，并设置缓存成功",dictId);
                }else{
                    logger.error("数据字典Id{}-不存在对应的子集列表",dictId);
                }
            }else{
                logger.error("数据字典Id{}-查询子集列表失败",dictId);
            }
        }else{
            for(int i = 0; i<dictList.size();i++){
            //循环遍历一级大类 查询是否存在二级类别
                Dict dict = dictList.get(i);
                Integer dictSonId = dictList.get(i).getDictId();
                List<Dict> dictSonList=new ArrayList<Dict>();
                Map<String,Object> allSon = RedisUtil.getJsonToMap(CacheUtil.dict_list_key+dictSonId);
                if(CollectionUtil.isNotEmpty(allSon)){
                    String dictListSonStr= allSon.get(CacheUtil.dict_list_key+dictSonId).toString();
                    dictSonList=JsonUtils.jsonToList(dictListSonStr,Dict.class);
                }
                if(!CollectionUtil.isEmpty(dictSonList)){//如果二级类别不为空 把二级类别中的数据加入到一级类别中
                    dict.setHasSonList(true);
                    dict.setSonList(dictSonList);
                }
            }
            logger.info("dictId:{},从缓存取值字典子集列表成功",dictId);
        }
        return ResultMessage.success(dictList);
    }


    @Override
    public ResultMessage queryNameByRegionId(Long regionId) {
        String regionName=RedisUtil.get(CacheUtil.region_name_key+regionId);
        if(StringUtils.isBlank(regionName)){
            ResultMessage result=regionService.getById(regionId);
            if(result.isFlag()){
                InitRegion d=(InitRegion)result.getData();
                regionName=d!=null?d.getChinaName():"";
                RedisUtil.set(CacheUtil.region_name_key+regionId,regionName);
                logger.info("regionId:{},查询区域名称，并设置缓存成功",regionId);
            }else{
                logger.error("区域ID{}-不存在对应的name",regionId);
            }
        }else{
            logger.info("regionId:{},从缓存取值区域名称成功",regionId);
        }
        return ResultMessage.success("查询成功",regionName);
    }

    @Override
    public ResultMessage queryCodeByRegionId(Long regionId) {
        String regionCode=RedisUtil.get(CacheUtil.region_code_key+regionId);
        if(StringUtils.isBlank(regionCode)){
            ResultMessage result=regionService.getById(regionId);
            if(result.isFlag()){
                InitRegion d=(InitRegion)result.getData();
                regionCode=d!=null?d.getRegionCode():"";
                RedisUtil.set(CacheUtil.region_code_key+regionId,regionCode);
                logger.info("regionId:{},查询区域编码，并设置缓存成功",regionId);
            }else{
                logger.error("区域ID{}-不存在对应的编码",regionId);
            }
        }else{
            logger.info("regionId:{},从缓存取值区域编码成功",regionId);
        }
        return ResultMessage.success("查询成功",regionCode);
    }

    @Override
    public ResultMessage findSubListByRegionId(Long regionId) {
        List<InitRegion> regionList=new ArrayList<InitRegion>();
        Map<String,Object> all = RedisUtil.getJsonToMap(CacheUtil.region_list_key+regionId);
        if(CollectionUtil.isNotEmpty(all)){
            String regionListStr= all.get(CacheUtil.region_list_key+regionId).toString();
            regionList=JsonUtils.jsonToList(regionListStr,InitRegion.class);
        }
        if(CollectionUtil.isEmpty(regionList)) {
            ResultMessage result = regionService.findRegionListByParentId(regionId);
            if (result.isFlag()) {
                regionList = (List<InitRegion>) result.getData();
                if (CollectionUtil.isNotEmpty(regionList)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(CacheUtil.region_list_key + regionId, regionList);
                    RedisUtil.setJsonStr(CacheUtil.region_list_key + regionId, map, expireSecond);
                    logger.info("regionId:{},查询数据库，并设置缓存成功", regionId);
                }else{
                    logger.error("区域Id{}-不存在对应的子集列表", regionId);
                }
            } else {
                logger.error("区域Id{}-查询子集列表失败", regionId);
            }
        }else{
            logger.info("regionId:{},从缓存取值区域子集列表成功",regionId);
        }
        return ResultMessage.success(regionList);
    }


    @Override
    public ResultMessage queryAllLeaf(Integer parentId) {

        ResultMessage result = findSubListByDictId(parentId); //根据parentId获取所有子节点

        List<Dict> mapList = new ArrayList<Dict>();

        if(result.isFlag()){

           List<Dict> dictList = (List<Dict>) result.getData();

           if(dictList != null && dictList.size() > 0 ){
               for(Dict dict : dictList){

                   if(dict.isLeaf()){
                       mapList.add(dict);
                   }else{
                       ResultMessage temp = findSubListByDictId(dict.getDictId()); //根据parentId获取所有子节点
                       if(temp.isFlag()){
                           List<Dict> tempList = (List<Dict>)temp.getData();
                           for(Dict dict2 : tempList){
                               if(dict2.isLeaf()){
                                   mapList.add(dict2);
                               }
                           }
                       }
                   }
               }
           }
        }

        return ResultMessage.success(mapList);
    }


    @Override
    public ResultMessage queryNameByAccountId(Integer accountId) {
        String accountName=RedisUtil.get(CacheUtil.account_name_key+accountId);
        if(StringUtils.isBlank(accountName)){
            ResultMessage result=permAccountService.getById(accountId);
            if(result.isFlag()){
                PermAccount d=(PermAccount)result.getData();
                if(d!=null){
                    accountName=StringUtils.isBlank(d.getNickName())?d.getAccountName():d.getNickName();
                }else{
                    accountName="";
                }
                RedisUtil.set(CacheUtil.account_name_key+accountId,accountName);
                logger.info("accountId:{},查询用户名称，并设置缓存成功",accountId);
            }else{
                logger.error("用户ID{}-不存在对应的用户名称",accountId);
            }
        }else{
            logger.info("accountId:{},从缓存取值用户名称成功",accountId);
        }
        return ResultMessage.success("查询成功",accountName);
    }

    @Override
    public ResultMessage findUserByAccountId(Integer accountId) {
        PermAccount permAccount=new PermAccount();
        Map<String,Object> all = RedisUtil.getJsonToMap(CacheUtil.account_obj_key+accountId);
        if(all!=null&&all.size()>0){
            String PermAccountStr= all.get(CacheUtil.account_obj_key+accountId).toString();
            permAccount=JsonUtils.jsonToPojo(PermAccountStr,PermAccount.class);
            logger.info("accountId:{},从缓存取值用户对象成功",accountId);
        }else {
            ResultMessage result=permAccountService.getById(accountId);
            if(result.isFlag()){
                permAccount=(PermAccount)result.getData();
                Map<String,Object> map=new HashMap<String,Object>();
                map.put(CacheUtil.account_obj_key+accountId,permAccount);
                RedisUtil.setJsonStr(CacheUtil.account_obj_key+accountId,map,expireSecond);
                logger.info("accountId:{},查询用户名称，并设置缓存成功",accountId);
            }else{
                logger.error("用户ID{}-查询用户失败",accountId);
            }
        }

        return ResultMessage.success(permAccount);
    }

    @Override
    public void initDictCache() {
        long startTime=new Date().getTime();
        logger.info("初始化所有数据字典缓存开始》》》》》》》》》》》》》》》》》！");
        //1.删除原有数据字典缓存
        Set <String > set=RedisUtil.getJedis().keys(CacheTypeEnum.CACHE_TYPE_DICT.getType()+"*");
        for(String str:set){
            RedisUtil.del(str);
        }
        logger.info("删除所有数据字典缓存成功！删除字典缓存个数：{}",(set!=null?set.size():0));
        //2.赋值缓存
        ResultMessage result=dictService.findAllDictList();
        if(result.isFlag()){
            List<Dict> dictList=(List<Dict> )result.getData();
            Map<String,Object> map=new HashMap<String,Object>();
            for(Dict dict:dictList){
                //缓存id-name
                RedisUtil.set(CacheUtil.dict_name_key+dict.getDictId(),dict.getDataName());
                //缓存id-code
                RedisUtil.set(CacheUtil.dict_code_key+dict.getDictId(),dict.getDataCode()!=null?dict.getDataCode():"");
                if(dict.getParentId()!=null){
                    String list_key=CacheUtil.dict_list_key+dict.getParentId();
                    if(map.containsKey(list_key)){
                        List<Dict> list=(List<Dict> )map.get(list_key);
                        list.add(dict);
                    }else {
                        List<Dict> list=new ArrayList<Dict>();
                        list.add(dict);
                        map.put(list_key,list);
                    }
                }
            }
            //缓存id-list
            Set<String>keySet=map.keySet();
            for(String key:keySet){
                Map<String,Object> dictMap=new HashMap<String,Object>();
                dictMap.put(key,map.get(key));
                RedisUtil.setJsonStr(key,dictMap,expireSecond);
                //logger.info("key:{},根据parentId设置字典子集列表缓存成功",key);
            }
            logger.info("缓存所有数据字典缓存成功！缓存字典名称个数：{},缓存字典编码个数：{},缓存字典子集列表个数：{}",new Integer [] {(dictList!=null?dictList.size():0),(dictList!=null?dictList.size():0),(keySet!=null?keySet.size():0)});
        }else{
            logger.error("查询数据字典错误:{}",result.getMsg());
        }
        //更新最后刷新时间
        RedisUtil.set(CacheUtil.CACHE_DICT_LASTTIME_KEY,DateUtil.dateToString(new Date(),"YYYY-MM-dd HH:mm:ss"));
        long endTime=new Date().getTime();
        logger.info("初始化所有数据字典缓存结束》》》》》》》》》》》》》》》》》！耗时："+(endTime-startTime)/1000.0+"秒");
    }

    @Override
    public void initRegionCache() {
        long startTime=new Date().getTime();
        logger.info("初始化所有区域缓存开始》》》》》》》》》》》》》》》》》！");
        //1.删除原有区域缓存
        Set <String > set=RedisUtil.getJedis().keys(CacheTypeEnum.CACHE_TYPE_REGION.getType()+"*");
        for(String str:set){
            RedisUtil.del(str);
        }
        logger.info("删除所有区域缓存成功！删除区域缓存个数：{}",(set!=null?set.size():0));
        //2.赋值缓存
        ResultMessage result=regionService.findAll();
        if(result.isFlag()){
            List<InitRegion> regionList=(List<InitRegion> )result.getData();
            Map<String,Object> map=new HashMap<String,Object>();
            for(InitRegion region:regionList) {
                //缓存id-name
                RedisUtil.set(CacheUtil.region_name_key+region.getRegionId(),region.getChinaName());
                //缓存id-code
                RedisUtil.set(CacheUtil.region_code_key+region.getRegionId(),region.getRegionCode()!=null?region.getRegionCode():"");
                //logger.info("regionId:{},根据regionId设置区域名称缓存成功",region.getRegionId());
                if(region.getParentId()!=null){
                    String list_key=CacheUtil.region_list_key+region.getParentId();
                    if(map.containsKey(list_key)){
                        List<InitRegion> list=(List<InitRegion> )map.get(list_key);
                        list.add(region);
                    }else {
                        List<InitRegion> list=new ArrayList<InitRegion>();
                        list.add(region);
                        map.put(list_key,list);
                    }
                }
            }
            //缓存id-list
            Set<String>keySet=map.keySet();
            for(String key:keySet){
                Map<String,Object> redisMap=new HashMap<String,Object>();
                redisMap.put(key,map.get(key));
                RedisUtil.setJsonStr(key,redisMap,expireSecond);
                //logger.info("key:{},根据parentId设置区域子集列表缓存成功",key);
            }
            logger.info("缓存所有区域缓存成功！缓存区域名称个数：{},缓存区域编码个数：{},缓存区域子集列表个数：{}",new Integer [] {(regionList!=null?regionList.size():0),(regionList!=null?regionList.size():0),(keySet!=null?keySet.size():0)});
        }else{
            logger.error("查询区域错误:{}",result.getMsg());
        }
        //更新最后刷新时间
        RedisUtil.set(CacheUtil.CACHE_REGION_LASTTIME_KEY,DateUtil.dateToString(new Date(),"YYYY-MM-dd HH:mm:ss"));
        long endTime=new Date().getTime();
        logger.info("初始化所有区域缓存结束》》》》》》》》》》》》》》》》》！耗时："+(endTime-startTime)/1000.0+"秒");
    }

    @Override
    public void initUserCache() {
        long startTime=new Date().getTime();
        logger.info("初始化所有用户缓存开始》》》》》》》》》》》》》》》》》！");
        //1.删除原有用户缓存
        Set <String > set=RedisUtil.getJedis().keys(CacheTypeEnum.CACHE_TYPE_ACCOUNT.getType()+"*");
        for(String str:set){
            RedisUtil.del(str);
        }
        logger.info("删除所有用户缓存成功！删除缓存个数：{}",(set!=null?set.size():0));
        //2.赋值缓存
        ResultMessage result=permAccountService.queryPermAccountAll();
        if(result.isFlag()){
            List<PermAccount> accountList=(List<PermAccount> )result.getData();
            for(PermAccount account:accountList){
                //缓存id-name
                String accountName=StringUtils.isBlank(account.getNickName())?account.getAccountName():account.getNickName();
                RedisUtil.set(CacheUtil.account_name_key+account.getAccountId(),StringUtils.isNotBlank(accountName)?accountName:account.getAccountId().toString());
                //logger.info("key:{},缓存用户名称成功！",CacheUtil.account_name_key+account.getAccountId());
                //缓存id-list
                Map<String,Object> map=new HashMap<String,Object>();
                map.put(CacheUtil.account_obj_key+account.getAccountId(),account);
                RedisUtil.setJsonStr(CacheUtil.account_obj_key+account.getAccountId(),map,expireSecond);
                //logger.info("key:{},缓存用户对象成功！",CacheUtil.account_obj_key+accountId);
            }
            logger.info("缓存所有用户成功！缓存用户名称和对象个数：{}",accountList.size());
        }else{
            logger.error("查询用户错误:{}",result.getMsg());
        }
        //更新最后刷新时间
        RedisUtil.set(CacheUtil.CACHE_ACCOUNT_LASTTIME_KEY,DateUtil.dateToString(new Date(),"YYYY-MM-dd HH:mm:ss"));
        long endTime=new Date().getTime();
        logger.info("初始化所有用户缓存结束》》》》》》》》》》》》》》》》》！耗时："+(endTime-startTime)/1000.0+"秒");
    }

    @Override
    public void initAllDataCache() {
        long startTime=new Date().getTime();
        logger.info("初始化全部缓存开始》》》》》》》》》》》》》》》》》！");
        initDictCache();
        initRegionCache();
        initUserCache();
        //更新最后刷新时间
        RedisUtil.set(CacheUtil.CACHE_LASTTIME_KEY,DateUtil.dateToString(new Date(),"YYYY-MM-dd HH:mm:ss"));
        long endTime=new Date().getTime();
        logger.info("初始化全部缓存结束》》》》》》》》》》》》》》》》》！耗时："+(endTime-startTime)/1000.0+"秒");
    }

    @Override
    public ResultMessage getCacheLastTime() {
        String dictLastTime=RedisUtil.get(CacheUtil.CACHE_DICT_LASTTIME_KEY);
        String regionLastTime=RedisUtil.get(CacheUtil.CACHE_REGION_LASTTIME_KEY);
        String accountLastTime=RedisUtil.get(CacheUtil.CACHE_ACCOUNT_LASTTIME_KEY);
        String allLastTime=RedisUtil.get(CacheUtil.CACHE_LASTTIME_KEY);
        String [] lastTimes={dictLastTime,regionLastTime,accountLastTime,allLastTime};
        return ResultMessage.success(lastTimes);
    }

    @Override
    public ResultMessage updateCacheDict(DictParam dict,Long parentId,boolean isDel) {
        if(isDel){
            ResultMessage result=dictService.findDictById(dict);
            if(result.isFlag()){
                dict=(DictParam)result.getData();
                parentId=dict.getParentId();
            }
        }
        //删除原值
        RedisUtil.del(CacheUtil.dict_name_key+dict.getDictId());
        RedisUtil.del(CacheUtil.dict_code_key+dict.getDictId());
        //非删除，重新设置
        if(!isDel) {
            RedisUtil.set(CacheUtil.dict_name_key + dict.getDictId(), dict.getDataName());
            RedisUtil.set(CacheUtil.dict_code_key + dict.getDictId(), dict.getDataCode());
        }
        if(parentId!=null){
            //删除原有列表缓存
            RedisUtil.del(CacheUtil.dict_list_key+parentId);
            //重新设置子集list缓存
            findSubListByDictId(parentId.intValue());
        }
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateCacheRegion(InitRegion region,boolean isDel) {
        ResultMessage result=regionService.getById(region.getRegionId());
        if(result.isFlag()){
            region=(InitRegion)result.getData();
        }
        //删除原值
        RedisUtil.del(CacheUtil.region_name_key+region.getRegionId());
        RedisUtil.del(CacheUtil.region_code_key+region.getRegionId());
        //非删除，则重新设置
        if(!isDel){
            RedisUtil.set(CacheUtil.region_name_key+region.getRegionId(),region.getRegionName());
            RedisUtil.set(CacheUtil.region_code_key+region.getRegionId(),region.getRegionCode());
        }
        if(region.getParentId()!=null) {
            //删除原有列表缓存
            RedisUtil.del(CacheUtil.region_list_key + region.getParentId());
            //重新设置子集list缓存
            findSubListByRegionId(region.getParentId());
        }
        return ResultMessage.success();
    }

    @Override
    public ResultMessage updateCacheUser(Integer accountId) {
        //删除原值
        RedisUtil.del(CacheUtil.account_name_key+accountId);
        //刷新缓存
        String accountName="";
        ResultMessage result=permAccountService.getById(accountId);
        if(result.isFlag()){
            PermAccount d=(PermAccount)result.getData();
            if(d!=null){
                accountName=StringUtils.isBlank(d.getNickName())?d.getAccountName():d.getNickName();
            }
            RedisUtil.set(CacheUtil.account_name_key+accountId,accountName);
        }
        return ResultMessage.success();
    }

}
