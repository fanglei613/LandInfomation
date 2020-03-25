package com.jh.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.Enum.UserEnum;
import com.jh.system.entity.PermRole;
import com.jh.system.entity.RelateRoleRes;
import com.jh.system.mapping.IPermRoleMapper;
import com.jh.system.model.RoleParam;
import com.jh.system.service.IPermRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


/**
 * 角色信息实现类
 * @version <1> 2018-01-23 lcw : Created.
 */
@Transactional
@Service
public class PermRoleServiceImpl extends BaseServiceImpl<RoleParam, PermRole, Integer> implements IPermRoleService {
    private Logger logger = Logger.getLogger(PermRoleServiceImpl.class);

    @Autowired
    private IPermRoleMapper roleMapper;

    protected IBaseMapper<RoleParam, PermRole, Integer> getDao() {
        return roleMapper;
    }

    public PageInfo<PermRole> findByPage(RoleParam roleParam) {
        PageHelper.startPage(roleParam.getPage(), roleParam.getRows());
        List<PermRole> list = roleMapper.findByPage(roleParam);
        return  new PageInfo<PermRole>(list);
    }

    public ResultMessage saveRelateResource(RoleParam roleParam) {
        if(roleParam != null){
            roleMapper.unRelateResource(roleParam.getRoleId());
            for(Integer resId : roleParam.getResIds()){
                RelateRoleRes relateRoleRes = new RelateRoleRes();
                relateRoleRes.setRoleId(roleParam.getRoleId());
                relateRoleRes.setResId(resId);
                roleMapper.relateResource(relateRoleRes);
            }
        }
        return ResultMessage.success();
    }

    public ResultMessage findAll(Integer accountId) {
        List<PermRole> list= roleMapper.findAll(accountId);
        Iterator<PermRole> iterator =  list.iterator();
        Integer superRoleId = new Integer(PropertyUtil.getPropertiesForConfig("DEFUALT_SUPER_ADMIN_ROLE_ID"));//超级管理员编号
        while(iterator.hasNext()){
            PermRole permRole = iterator.next();
            Integer account = permRole.getAccountId();
            Integer roleId = permRole.getRoleId();
            //如果之前没有设置超级管理，那么就过滤超级管理员，
            if(account == null && roleId.equals(superRoleId)){
                iterator.remove();
            }
        }
        return ResultMessage.success(list);
    }

    public ResultMessage checkRoleCode(String roleCode) {
        ResultMessage result = ResultMessage.fail();
        PermRole role = roleMapper.getByRoleCode(roleCode);
        if(role != null){
            result = ResultMessage.success();
        }

        return result;
    }

    /**
     * 根据用户ID获取用户信息
     * @param accountId
     * @return
     * @version<1> 2018-03-18 lcw :Created.
     */
    @Override
    public ResultMessage findRoleByAccountId(Integer accountId) {
         if(accountId == null){
            return ResultMessage.fail(UserEnum.ACCOUNT_NAME_NOT_EXIST.getValue(), UserEnum.ACCOUNT_NAME_NOT_EXIST.getMesasge());
        }
        List<PermRole> list = roleMapper.findRoleByAccountId(accountId);

        return ResultMessage.success(list);
    }

    @Override
    public ResultMessage isExistRole(Integer accountId,String roleCode){
        PermRole role=new PermRole();
        role.setAccountId(accountId);
        role.setRoleCode(roleCode);
        Integer flag=roleMapper.isExistRole(role);
        return ResultMessage.success(flag);
    }
}
