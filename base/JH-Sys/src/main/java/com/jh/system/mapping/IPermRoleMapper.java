package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.PermRole;
import com.jh.system.entity.RelateRoleRes;
import com.jh.system.model.RoleParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 *
 * @version <1> 2018-01-23 lcw： Created.
 */
@Mapper
public interface IPermRoleMapper extends IBaseMapper<RoleParam,PermRole,Integer> {
    /**
     * @description: 查询所有角色信息
     * @return
     * @version <1> 2018-01-25 cxj： Created.
     */
    List<PermRole> findAll(Integer accountId);

    /**
     * @description: 根据角色编码获取角色信息
     * @param roleCode
     * @return
     */
    PermRole getByRoleCode(String roleCode);

    /**
     * 角色关联菜单
     * @param relateRoleRes
     * @return
     * @version <1> 2017-02-05 cxj : Created.
     */
    void relateResource(RelateRoleRes relateRoleRes);

    /**
     * 根据角色主键，取消菜单权限关联
     * @param roleId
     * @return
     * @version <1> 2017-02-05 cxj : Created.
     */
    void unRelateResource(Integer roleId);

    /**
     * 根据用户ID获取用户信息
     * @param accountId
     * @return
     * @version<1> 2018-03-18 lcw :Created.
     */
    List<PermRole> findRoleByAccountId(Integer accountId);

    /**
     * 根据用户ID是否存在某角色
     * @param accountId
     * @return
     * @version<1> 2018-03-18 lijie :Created.
     */
    Integer isExistRole(PermRole role);
}
