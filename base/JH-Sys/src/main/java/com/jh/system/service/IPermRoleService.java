package com.jh.system.service;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermRole;
import com.jh.system.model.RoleParam;

/**
 * @description: 角色信息接口
 * @version <1> 2018-01-17 cxj： Created.
 */
public interface IPermRoleService extends IBaseService<RoleParam, PermRole , Integer> {

    /**
     * 角色信息分页查询
     * @param roleParam
     * @return
     */
    public PageInfo<PermRole> findByPage(RoleParam roleParam);

    /**
     * 角色关联菜单（一对多）
     * 注意事务：涉及一对多的多表插入操作
     * @param roleParam
     * @return
     * @version <1> 2017-02-05 cxj : Created.
     */
    public ResultMessage saveRelateResource(RoleParam roleParam);

    /**
     *  @description: 获取所有角色
     *  @return
     *  @version <1> 2018-01-25 cxj： Created.
     */
    ResultMessage findAll(Integer accountId);

    /**
     * @description: 检索角色编码是否存在
     * 1.检索成功，则表示角色编码已存在，返回true
     * 2.检索为空，则表示角色编码不存在，返回false
     * @param roleCode
     * @return
     * @version <1> 2018-01-26 cxj： Created.
     */
    ResultMessage checkRoleCode(String roleCode);

    /**
     * 根据用户ID获取用户角色
     * @param accountId
     * @return
     * @version<1> 2018-03-18 lcw:Created.
     */
    ResultMessage findRoleByAccountId(Integer accountId);

    /**
     * 用户是否存在某角色色
     * @param accountId
     * @return
     * @version<1> 2019-03-18 lijie:Created.
     */
    ResultMessage isExistRole(Integer accountId,String roleCode);

}
