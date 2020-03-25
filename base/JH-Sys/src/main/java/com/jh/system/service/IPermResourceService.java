package com.jh.system.service;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermResource;
import com.jh.system.model.ResourceParam;

import java.util.Map;

/**
 * @description: 资源信息接口
 * @version <1> 2018-01-17 cxj： Created.
 */
public interface IPermResourceService extends IBaseService<ResourceParam, PermResource, Integer> {

    /**
     * @description: 根据角色ID获取所有菜单信息
     * @param roleId 角色主键
     * @return
     * @version <1> 2018-01-29 cxj： Created.
     */
    ResultMessage findAll(Integer roleId);

    /**
     * 查询所有的资源
     * @return
     * @version <1> 2018-04-03 xzg： Created.
     */
    ResultMessage findAllResource();

    /**
     * @description: 分页查询
     * @param resourceParam
     * @return
     * @version <1> 2018-02-02 cxj： Created.
     */
    PageInfo<PermResource> findByPage(ResourceParam resourceParam);

    /**
     * @description: 校验菜单编码是否存在
     * 若为true，表示存在，不可重复添加
     * 若未false，表示不存在，可进行添加
     * @param resourceParam
     * @return
     * @version <1> 2018-02-02 cxj： Created.
     */
    ResultMessage checkResCode(ResourceParam resourceParam);

    /**
     * @description: 根据用户id查询菜单权限
     * @param accountId 用户主键
     * @return
     * @version <1> 2018-03-06 wl： Created.
     */
    ResultMessage findMenu(Integer accountId, Integer roleType);


    /**
     * @description: 根据用户id查询详细信息
     * @param resId 资源主键
     * @return
     * @version <1> 2018-03-15 wl： Created.
     */
    ResultMessage findById(Integer resId);

    /**
     * @description: 根据用户id查询按钮权限
     * @param accountId 用户主键
     * @param resCode 资源编码
     * @return
     * @version <1> 2018-03-19 cxj： Created.
     */
    ResultMessage findButton(Integer accountId, String resCode);

    /**
     * 添加资源
     * @param permResource
     * @return
     * @version <1> 2018-04-04 xzg： Created.
     */
    ResultMessage saveResource(PermResource permResource);

    /**
     * @description: 检索该资源是否存在子节点
     * 1.检索成功，则表示存在子节点，返回true
     * 2.检索为空，则表示不存在子节点，返回false
     * @param resId
     * @return
     * @version <1> 2018-04-08 wl： Created.
     */
    ResultMessage checkResleaf(Integer resId);

    /**
     * 修改资源
     * @param permResource 资源实体类
     * @return 返回修改后的记录数
     * @version <1> 2018-05-29 lxy： Created.
     */
    public ResultMessage updateResource(PermResource permResource);

    /**
     * 根据父节点获取对应的子节点。
     * @param parentId 资源父节点
     * @version <1> 2018-05-29 lxy： Created.
     */
    ResultMessage findResourceByParentId(Integer parentId);

    /**
     * 根据角色查询子系统信息集合
     * @param accountId
     * @return
     * @version<1> 2018-07-09 lcw :Created.
     */
    ResultMessage findSubSystemByAccountId(Integer accountId);

    ResultMessage queryAppMenus(Map<String,Object> voMap);
}
