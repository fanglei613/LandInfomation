package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.RelateAccountRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @version <1> 2018-01-23 lcw： Created.
 */
@Mapper
public interface IRelateAccountRoleMapper extends IBaseMapper<RelateAccountRole,RelateAccountRole,Integer> {

    RelateAccountRole findByAccountId(RelateAccountRole relateAccountRole);



    /**
     * 添加账号角色关联信息
     * @param relateAccountRoleMap(accountId:账号ID,roleId :角色Id)
     * @return   ResultMessage
     * @version <1> 2018-05-09 cxw： Created.
     */
    public int addAccountRole(Map<String,Object> relateAccountRoleMap);

    /**
     * 查询用户拥有的角色
     * @param params  (accountId , dataStatus , delFlag)
     * @return   (accountId, roleId , roleCode , roleName , roleType)
     * @version <1> 2018-05-16 xzg： Created.
     */
    public List<Map<String,Object>> findRoleByAccountId(Map<String,Object> params);
}


