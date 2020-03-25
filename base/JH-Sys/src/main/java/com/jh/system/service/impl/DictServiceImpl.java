package com.jh.system.service.impl;

import com.jh.cache.service.ICacheService;
import com.jh.constant.ConstantUtil;
import com.jh.constant.SysConstant;
import com.jh.enums.DataStatusEnum;
import com.jh.enums.DelStatusEnum;
import com.jh.system.Enum.DictEnum;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.Dict;
import com.jh.system.entity.PermAccount;
import com.jh.system.mapping.IDictMapper;
import com.jh.system.mapping.IPermAccountMapper;
import com.jh.system.model.DictParam;
import com.jh.system.model.DictTreeReturn;
import com.jh.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class DictServiceImpl extends BaseServiceImpl<DictParam, Dict, Integer> implements IDictService {
    @Autowired
    private IDictMapper dictMapper;

    @Autowired
    private IPermAccountMapper permAccountMapper;

    @Autowired
    private ICacheService cacheService;

    /**
     *查询数据字典所有数据
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    @Override
    public ResultMessage findDictList() {
        ResultMessage result = ResultMessage.success();
        List<DictTreeReturn> list  = dictMapper.findDictList();
        result.setData(list);
        return result;
    }

    /**
     *查询数据字典所有数据
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    @Override
    public ResultMessage findAllDictList() {
        ResultMessage result = ResultMessage.success();
        List<Dict> list  = dictMapper.findAllDictList();
        result.setData(list);
        return result;
    }

    /**
     *  根据父ID查询子节点
     * @return DictTreeReturn
     * @version  <1> 2017-12-27 cxw : Created.:
     */
    @Override
    public ResultMessage findDictListByPid(DictTreeReturn dtr) {
        ResultMessage result = ResultMessage.success();
        List<DictTreeReturn> list = new ArrayList<DictTreeReturn>();
        if(dtr.getId()!=null) {
            list = dictMapper.findDictListByPid(Long.valueOf(dtr.getId()));
            for (int i = 0; i < list.size(); i++) {
                DictTreeReturn dtrReturn = new DictTreeReturn();
                //判断是否有子节点，有则设置isParent为true;
                if (list.get(i).getId() != null) {
                    Long id = Long.parseLong(list.get(i).getId().toString());
                    List<DictTreeReturn> dicts = new ArrayList<DictTreeReturn>();
                    dicts = dictMapper.findDictListByPid(id);
                    if (dicts.size() > 0) {
                        dtrReturn.setId(Integer.parseInt(list.get(i).getId().toString()));
                        dtrReturn.setName(list.get(i).getName());
                        dtrReturn.setpId(list.get(i).getpId());
                        dtrReturn.setParent(true);
                        list.remove(i);
                        list.add(i, dtrReturn);
                    }
                }
            }
            result.setData(list);
            return result;
        }
        else {
            result = ResultMessage.fail();
            result.setMsg("父ID不能为空");
            return result;
        }

    }

    /**
     *根据id查询数据字典数据
     * @param dict 字典对象
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    @Override
    public ResultMessage findDictById(DictParam dict) {
        ResultMessage result = ResultMessage.success();
        if(dict.getDictId()!=null)
        {
            DictParam dictParam = new DictParam();
            dictParam =  dictMapper.findDictById(dict.getDictId());
            result.setData(dictParam);
        }
        else {
            result = ResultMessage.fail();
            result.setMsg("字典ID不能为空");
        }
        return result;
    }


   public ResultMessage queryDictByParentId(Dict dict) {
        List<Dict> list = dictMapper.queryDictByParentId(dict);
        return ResultMessage.success(list);
    }

    public ResultMessage queryDictByParentIdList(List<Integer> list) {
        List<Dict> dictList = dictMapper.queryDictByParentIdList(list);
        Map<Integer,List<Dict>> map = new HashMap<Integer,List<Dict>>();
        for(Integer id : list){
            List<Dict> tmp = new LinkedList<Dict>();
            for(Dict dict : dictList){
                if(id.intValue() == dict.getParentId().intValue()){
                    tmp.add(dict);
                }
            }
            map.put(id,tmp);
        }
        return ResultMessage.success(map);
    }

    @Override
    protected IBaseMapper<DictParam, Dict, Integer> getDao() {
        return dictMapper;
    }

    /**
     *添加数据字典数据
     * @param dict 数据字典对象
     * @return int 添加成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    @Override
    public ResultMessage addDict(DictParam dict,HttpServletRequest request) {
        ResultMessage result =dict.checkAddParam();
        if(result.isFlag())
        {
            Integer idNum = dictMapper.checkDictIdIsExists(dict.getDictId());
            if(idNum==0)
            {
                Integer codeNum = dictMapper.checkDictCodeIsExists(dict.getDataCode());
                if(codeNum==0) {
                    PermAccount permAccount = (PermAccount) request.getSession().getAttribute(ConstantUtil.CURRENT_PERMACCOUNT);
                    if (null != permAccount) {
                        dict.setCreator(permAccount.getAccountId());
                        dict.setCreatorName(permAccount.getNickName());
                    }
                    dict.setDataType("1");
                    int num = dictMapper.addDict(dict);
                    if (num > 0) {

                        result.setData(dict);
                        result.setMsg("添加成功");
                        //刷新缓存
                        cacheService.updateCacheDict(dict,dict.getParentId(),false);
                    } else {
                        result = ResultMessage.fail();
                        result.setMsg("添加失败");
                    }
                }
                else {
                    result = ResultMessage.fail();
                    result.setMsg("字典编码不能重复");
                    result.setCode("code");
                }
            }
            else {
                result = ResultMessage.fail();
                result.setMsg("字典ID不能重复");
                result.setCode("id");
            }
        }
        return result;
    }

    /**
     *修改数据字典数据
     * @param dict 数据字典对象
     * @return int 修改成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    @Override
    public ResultMessage updateDict(DictParam dict,HttpServletRequest request) {
        ResultMessage result =dict.checkEditParam();
        if(result.isFlag())
        {
            PermAccount permAccount = (PermAccount) request.getSession().getAttribute(ConstantUtil.CURRENT_PERMACCOUNT);
            if (null != permAccount) {
                dict.setCreator(permAccount.getAccountId());
                dict.setCreatorName(permAccount.getNickName());
            }
            int num = dictMapper.editDict(dict);
            if(num>0) {
                int dictId = dict.getDictId();//原始编号
                List<DictTreeReturn>  childs = dictMapper.findDictListByPid(new Long(dictId));//获取下面的子节点
                if(childs.size()>0){
                    dictMapper.editDictStatusByParentId(dict);//设置当前节点子节点的状态。
                    reverse(childs,dict.getDataStatus());//递归修改数据状态
                }
                result.setData(dict);
                result.setMsg("保存成功");
                //刷新缓存
                cacheService.updateCacheDict(dict,dict.getParentId(),false);
            }else {
                result = ResultMessage.fail();
                result.setMsg("保存失败");
            }
        }
        return result;
    }

    /**
     * 递归修改
     * @param childs 子节点
     * @param dataStatus 数据状态
     */
    public void reverse(List<DictTreeReturn> childs,String dataStatus){
        if(childs.size()>0){
            for(DictTreeReturn child : childs){
                DictParam childDict = new DictParam();
                childDict.setDictId(child.getId());//原始编号
                childDict.setDataStatus(dataStatus);//状态
                dictMapper.editDictStatusByParentId(childDict);//设置当前节点子节点的状态。
                childs = dictMapper.findDictListByPid(new Long(child.getId()));
                if(childs.size()>0){
                    reverse(childs,dataStatus);
                }
            }
        }
    }

    /**
     *删除数据字典数据
     * @param dict 字典
     * @return int 删除成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    @Override
    public ResultMessage delDictById(DictParam dict,HttpServletRequest request) {
        ResultMessage result = ResultMessage.success();
        if(dict.getDictId()!=null)
        {
           // DictParam dict = new DictParam();
            PermAccount permAccount = (PermAccount) request.getSession().getAttribute(ConstantUtil.CURRENT_PERMACCOUNT);
            if (null != permAccount) {
                dict.setModifierName(permAccount.getNickName());
                dict.setModifier(permAccount.getAccountId());
            }
            dict.setDelFlag(ConstantUtil.SET_DEL_FLAG_IS_DELETE_STATE);
            dict.setDataStatus("0");
            int num = dictMapper.editDict(dict);
            if(num>0) {
                result.setMsg("删除成功");
                //更新缓存
                cacheService.updateCacheDict(dict,dict.getParentId(),true);

            }
            else {
                result = ResultMessage.fail();
                result.setMsg("删除失败");
            }
        }
        else {
            result = ResultMessage.fail();
            result.setMsg("请求参数错误");
        }
        return result;
    }


    public ResultMessage queryDataType() {
        List<DictParam> dictParamList = dictMapper.queryDataType();
        if (dictParamList == null || dictParamList.size() == 0) {
            return ResultMessage.fail();
        }
        return ResultMessage.success(dictParamList);
    }

    @Override
    public ResultMessage checkDictIdIsExists(Integer dictId) {
        Integer cnt = dictMapper.checkDictIdIsExists(dictId);
        if (cnt != null && cnt > 0) {
            return ResultMessage.success("1", "查询字典主键存在");
        }
        return ResultMessage.success("0", "查询字典主键不存在");
    }

    @Override
    public ResultMessage checkDictCodeIsExists(String dataCode) {
        Integer cnt = dictMapper.checkDictCodeIsExists(dataCode);
        if (cnt != null && cnt > 0) {
            return ResultMessage.success("1", "查询数据编码存在");
        }
        return ResultMessage.success("0", "查询数据编码不存在");
    }

    /**
     *  根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
     * @param dict 字典对象
     * @return  dict 字典对象集合
     * @version  <1> 2018-03-06 cxw : Created.:
     */
    @Override
    public ResultMessage findDictByCode(DictParam dict) {
        ResultMessage result = ResultMessage.success();
        if(!"".equals(dict.getDataCode())&&dict.getDataCode()!=null)
        {
            List<DictParam> dictParams = new ArrayList<DictParam>();
            dictParams = dictMapper.findDictByCode(dict.getDataCode());
            result.setData(dictParams);
            return result;
        }
        else{
            result = ResultMessage.fail();
            result.setMsg("编码不能为空");
            return result;
        }
    }

    @Override
    public ResultMessage checkDictCode(DictParam dictParam) {
        ResultMessage result = ResultMessage.success();
        //首先判断editId是否为空 如果为空则为新增  直接查询id是否重复
        //如果不为空 则先比对editId与id是否一致，如果不一致则需要查询editId是否重复
        //如果相同则不作重复性验证
        //code同上
        Integer idnum=1;
        if(dictParam.getEditId()!=null){//修改验证
            if(dictParam.getEditId().equals(dictParam.getDictId())){//修改id
                idnum=0;//id与原始id相同不作验证
            }else{
                idnum = dictMapper.checkDictIdIsExists(dictParam.getDictId());

            }
        }else{//新增验证
            idnum = dictMapper.checkDictIdIsExists(dictParam.getDictId());
        }
        if(idnum>0){//已有相同id
            return ResultMessage.success(DictEnum.DICT_ID_REPEAT.getValue(), DictEnum.DICT_ID_REPEAT.getMesasge());
        }else {
            result.setFlag(false);
        }
        Integer codeNum=1;
        if(dictParam.getEditCode()!=null){//修改验证
            if(dictParam.getDataCode().equals(dictParam.getEditCode())){
                codeNum=0;
            }else {
                codeNum=dictMapper.checkDictCodeIsExists(dictParam.getDataCode());
            }
        }else {
            codeNum=dictMapper.checkDictCodeIsExists(dictParam.getDataCode());
        }
        if(codeNum>0){//已有相同编码
            return ResultMessage.success(DictEnum.DICT_CODE_REPEAT.getValue(), DictEnum.DICT_CODE_REPEAT.getMesasge());
        }else {
            result.setFlag(false);
        }
        return result;
    }

    @Override
    public ResultMessage findCropByRegionId(Integer regionId) {
        List<DictParam> dictParamList = dictMapper.findCropByRegionId(regionId);
        return ResultMessage.success(dictParamList);
    }

    @Override
    public ResultMessage querySubDictListByParentId(Integer dictParentId) {
        try {
            List<DictParam> dictParamList = dictMapper.querySubDictListByParentId(dictParentId);
            for (DictParam dp : dictParamList) {
                List<DictParam> dictParamList2 = dictMapper.querySubDictListByParentId(dp.getDictId());
               /* if (dictParamList2.size() > 0) {
                    dp.setParent(true);
                }*/
            }
            return ResultMessage.success(dictParamList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.fail();
        }
    }




    @Override
    public ResultMessage findDictByName(String dictNameStr) {
        List<String> dictNameList = Arrays.asList(dictNameStr.split(","));
        List<Dict> dictEntityList =  dictMapper.findDictByName(dictNameList);
        return ResultMessage.success(DictEnum.QUERY_DICT_SUCCESS.getKey(),DictEnum.QUERY_DICT_SUCCESS.getValue(), dictEntityList);
    }

    @Override
    public ResultMessage findAccuracyByDsId(Integer dsId) {
        if (dsId == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("datasetId",dsId);
        params.put("dataStatus", DataStatusEnum.Valid.getCode());
        params.put("delFlag", DelStatusEnum.Normal.getCode());
        List<Map<String,Object>> dictList =  dictMapper.findAccuracyByDsId(params);
        return ResultMessage.success(dictList);
    }

    @Override
    public ResultMessage findDictByParamId(Integer parentId) {
        if (parentId == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId",parentId);
        params.put("dataStatus", DataStatusEnum.Valid.getCode());
        params.put("delFlag", DelStatusEnum.Normal.getCode());
        List<Map<String,Object>> dictList = dictMapper.findDictByParam(params);
        return ResultMessage.success(dictList);
    }

    @Override
    public ResultMessage queryDictByCode(String dataCode) {
        Dict dict=new Dict();
        dict.setDataCode(dataCode);
        dict = dictMapper.queryDictByCode(dict);
        return ResultMessage.success(dict);
    }
}
