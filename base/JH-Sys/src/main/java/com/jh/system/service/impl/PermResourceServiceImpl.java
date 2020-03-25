package com.jh.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.system.Enum.RoleTypeEnum;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.vo.ResultMessage;
import com.jh.system.Enum.ResourceEnum;
import com.jh.system.entity.PermResource;
import com.jh.system.mapping.IPermResourceMapper;
import com.jh.system.model.ResourceParam;
import com.jh.system.service.IPermResourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源信息实现类
 * @version <1> 2018-01-23 lcw : Created.
 */
@Transactional
@Service
public class PermResourceServiceImpl extends BaseServiceImpl<ResourceParam, PermResource, Integer> implements IPermResourceService {
    private Logger logger = Logger.getLogger(PermResourceServiceImpl.class);

    @Autowired
    private IPermResourceMapper resourceMapper;

    protected IBaseMapper<ResourceParam, PermResource, Integer> getDao() {
        return resourceMapper;
    }

    public ResultMessage findAll(Integer roleId) {
        List<PermResource> list ;
        if(roleId == null){
            list = resourceMapper.findAll();
        }else{
            list = resourceMapper.findAllWithRole(roleId);
        }

        return ResultMessage.success(list);
    }

   public ResultMessage findAllResource(){
       return ResultMessage.success(resourceMapper.findAllResource());
    }

    public PageInfo<PermResource> findByPage(ResourceParam resourceParam) {
        PageHelper.startPage(resourceParam.getPage(), resourceParam.getRows());
        PageHelper.orderBy(resourceParam.getSidx() + " " + resourceParam.getSord());
        List<PermResource> list =  resourceMapper.findByPage(resourceParam);
        return new PageInfo<PermResource>(list);
    }

    public ResultMessage checkResCode(ResourceParam resourceParam) {
        ResultMessage result = ResultMessage.success();
        PermResource resource = resourceMapper.getByResCode(resourceParam.getResCode());
        if(resource!=null){//已有相同编码
            return ResultMessage.success(ResourceEnum.RESOURCE_CODE_REPEAT.getValue(), ResourceEnum.RESOURCE_CODE_REPEAT.getMesasge());
        }else {
            result.setFlag(false);
        }
        PermResource resourceName=resourceMapper.getByResName(resourceParam.getResName());
        if(resourceName!=null){//已有相同名称
            return ResultMessage.success(ResourceEnum.RESOURCE_NAME_REPEAT.getValue(), ResourceEnum.RESOURCE_NAME_REPEAT.getMesasge());
        }else {
            result.setFlag(false);
        }
        return result;
    }

    @Override
    public ResultMessage findMenu(Integer accountId, Integer roleType) {
        Map<String,Object> map=new HashMap();
        map.put("accountId",accountId);
        map.put("resType",2);
        if(roleType == null){
            roleType = RoleTypeEnum.SYSTEM_ROLE_WEB.getId();
        }
        map.put("roleType", roleType); //所属工程
        List<PermResource> list= resourceMapper.findMenu(map);
        return ResultMessage.success(list);
    }


    @Override
    public ResultMessage findById(Integer resId) {
        PermResource resource = resourceMapper.findById(resId);
        return ResultMessage.success(resource);
    }

    public ResultMessage findButton(Integer accountId,String resCode) {
        try{
            Map<String,Object> map=new HashMap();
            map.put("accountId",accountId);
            map.put("resCode",resCode);
            PermResource permResource = resourceMapper.findButton(map);
            if(permResource == null){
                return ResultMessage.fail(ResourceEnum.RESOURCE_BUTTON_NO_JURISDICTION.getValue(),ResourceEnum.RESOURCE_BUTTON_NO_JURISDICTION.getMesasge());
            }
            return ResultMessage.success();
        }catch (Exception e){
            return ResultMessage.fail();
        }
    }

    public ResultMessage saveResource(PermResource permResource){
        resourceMapper.save(permResource);
        return ResultMessage.success(permResource);
    }

    @Override
    public ResultMessage checkResleaf(Integer resId) {
        ResultMessage result = ResultMessage.fail();
        ResourceParam resourceParam=new ResourceParam();
        resourceParam.setParentId(resId);
        List<PermResource> list =  resourceMapper.findResourceByParentId(resourceParam);
        if(list.size()>0){
            result = ResultMessage.success();
        }
        return result;
    }

    /**
     * 修改资源
     * @param permResource 资源实体类
     * @return 返回修改后的记录数
     * @version <1> 2018-05-29 lxy： Created.
     */
    @Override
    public ResultMessage updateResource(PermResource permResource) {
        int effectNum = resourceMapper.updateResouce(permResource);
        if(effectNum>0){
            Integer resId = permResource.getResId();//资源编号
            ResourceParam param = new ResourceParam();
            param.setParentId(resId);
            List<PermResource> childs = resourceMapper.findResourceByParentId(param);//获得子节点
            String dataStatus = permResource.getDataStatus();//数据状态
            if(childs.size()>0){
                PermResource parentParam = new PermResource();
                parentParam.setResId(resId);//设置资源编号
                parentParam.setDataStatus(dataStatus);//设置数据状态
                resourceMapper.updateResouceForStatus(parentParam);//设置当前节点的状态。
                recursive(childs,dataStatus);//递归修改
            }
        }else{
            ResultMessage.fail();
        }
        return ResultMessage.success(permResource);
    }

    /**
     * 根据父节点获取对应的子节点。
     * @param parentId 资源父节点
     * @version <1> 2018-05-29 lxy： Created.
     */
    @Override
    public ResultMessage findResourceByParentId(Integer parentId) {
        ResourceParam param = new ResourceParam();
        param.setParentId(parentId);
        List<PermResource>  resourceList = resourceMapper.findResourceByParentId(param);
        if(resourceList.size()>0){
            for(PermResource resource : resourceList){
                Integer resId = resource.getResId();
                param.setParentId(resId);
                List<PermResource>  sizeList = resourceMapper.findResourceByParentId(param);
                if(sizeList.size()>0){
                    resource.setIsParent(true);
                }else{
                    resource.setIsParent(false);
                }
            }
            return ResultMessage.success(resourceList);
        }else{
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage findSubSystemByAccountId(Integer accountId) {
        if(accountId == null ){
            return ResultMessage.fail("账号ID不能为空");
        }
        List<PermResource> list = resourceMapper.findSubSystemByAccountId(accountId);
        return ResultMessage.success(list);
    }

    /**
     * 递归修改
     * @param childs 资源树子节点
     * @param dataStatus 数据状态
     */
    private void recursive(List<PermResource> childs,String dataStatus){
        if(childs.size()>0){
            for(PermResource child : childs ){
                child.setDataStatus(dataStatus);
                resourceMapper.updateResouceForStatus(child);//设置当前节点的状态。
                Integer resId = child.getResId();//资源编号
                ResourceParam param = new ResourceParam();
                param.setParentId(resId);
                childs = resourceMapper.findResourceByParentId(param);//获得子节点
                if(childs.size()>0){
                    recursive(childs,dataStatus);
                }
            }
        }
    }

    @Override
    public ResultMessage queryAppMenus(Map<String,Object> paramMap) {
        List<Map<String,Object>> list = resourceMapper.queryAppMenus(paramMap);
        if(list != null && list.size() > 0){
            return ResultMessage.success(list);
        }
        return ResultMessage.fail("该用户无APP访问权限");
    }
}
