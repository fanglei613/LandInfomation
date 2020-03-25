package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.PermResource;
import com.jh.system.model.ResourceParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @version <1> 2018-01-23 lcw： Created.
 */
@Mapper
public interface IPermResourceMapper extends IBaseMapper<ResourceParam,PermResource,Integer> {
    /**
     * @description: 获取所有菜单数据
     * @return
     * @version <1> 2018-01-29 cxj： Created.
     */
    List<PermResource> findAll();

    /**
     * @description: 根据所有菜单数据,并显示已关联的角色
     * @param roleId 角色主键
     * @return
     * @version <1> 2018-01-29 cxj： Created.
     */
    List<PermResource> findAllWithRole(Integer roleId);

    /**
     * @description: 根据资源编码获取资源信息
     * @param resCode
     * @return
     * @version <1> 2018-02-02 cxj： Created.
     */
    PermResource getByResCode(@Param(value = "resCode") String resCode);

    /**
     * @description: 根据资源编码获取资源信息
     * @param resName
     * @return
     * @version <1> 2018-04-16 wl： Created.
     */
    PermResource getByResName(@Param(value = "resName") String resName);

    /**
     * @description: 根据用户id查询菜单权限
     * @param map 用户主键 资源类型（菜单）
     * @return
     * @version <1> 2018-03-06 wl： Created.
     */
    List<PermResource> findMenu(Map<String, Object> map);

    /**
     * @description: 根据资源id获取资源信息
     * @param resId
     * @return
     * @version <1> 2018-03-15 wl： Created.
     */
    PermResource findById(Integer resId);

    /**
     * @description: 根据用户id查询按钮权限
     * @param map
     * @return
     * @version <1> 2018-03-19 cxj： Created.
     */
    PermResource findButton(Map<String, Object> map);

    /**
     * 查询所有的资源
     * @return
     *  @version <1> 2018-04-03 xzg： Created.
     */
    List<PermResource> findAllResource();

    /**
     * 修改资源
     * @param resource 资源实体
     * @return 返回操作记录数
     * @version <1> 2018-05-29 lxy： Created.
     */
    int updateResouce(PermResource resource);

    /**
     * 修改资源状态
     * @param resource
     * @return 资源实体
     * @version <1> 2018-05-29 lxy： Created.
     */
    int updateResouceForStatus(PermResource resource);

    /**
     * 根据父节点查询所有的子节点
     * @param resourceParam 父节点
     * @returnr
     * @version <1> 2018-05-29 lxy： Created.
     */
    List<PermResource> findResourceByParentId(ResourceParam resourceParam);

    List<PermResource> findSubSystemByAccountId(Integer accountId);

    List<Map<String,Object>> queryAppMenus(Map<String, Object> paramMap);
}
