package com.jh.system.service;

import com.jh.system.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.RelateAccountRole;

import java.util.Map;

/**
 * @description:  账号与角色关联信息接口
 * @version <1> 2018-01-23 lcw： Created.
 */
public interface IRelateAccountRoleService extends IBaseService<RelateAccountRole, RelateAccountRole , Integer> {

    public ResultMessage setDefaultPermission(Integer accountId);


    /**
     * 添加账号角色关联信息
     * @param relateAccountRoleMap(accountId:账号ID,roleId :角色Id)
     * @return   ResultMessage
     * @version <1> 2018-05-09 cxw： Created.
     */
    public ResultMessage addAccountRole(Map<String,Object> relateAccountRoleMap);

    /**
     * 查询账号拥有的角色
     * @param accountId
     * @return
     * @version <1> 2018-05-16 xzg： Created.
     */
    public ResultMessage findRolesByAccountId(Integer accountId);

}
