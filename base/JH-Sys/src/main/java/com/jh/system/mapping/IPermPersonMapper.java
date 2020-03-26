package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.PermPerson;
import com.jh.system.entity.RelateAccountRole;
import org.apache.ibatis.annotations.Mapper;
import com.jh.system.model.PersonParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @version <1> 2018-01-23 lcw： Created.
 */
@Mapper
public interface IPermPersonMapper extends IBaseMapper<PersonParam,PermPerson,Integer> {

    /**
     *  @description: 删除关联角色信息
     *  @version <1> 2018-01-24cxj： Created.
     */
    void relateRole(RelateAccountRole relateAccountRole);

    /**
     *  @description: 保存角色关联信息
     *  @version <1> 2018-01-24cxj： Created.
     */
    void unRelateRole(Integer accountId);

    /**
     * 根据账号查询用户信息
     * @param accountName
     * @return
     * @version<1> 2018-03-09 lcw : Created.
     */
    PermPerson queryByAccountName(String accountName);

    /**
     * 根据账号ID查询用户类型，电话号码
     * @param acountId 账号ID
     * @return PermPerson 用户类型
     * @version <1> 2018-03-20 cxw： Created.
     */
    Integer queryPersonType(Integer acountId);

    /**
     * 根据账号ID查询用户信息
     * @param accountId
     * @version <1> 2018-03-21 cxj： Created.
     */
    PermPerson findPersonByAccountId(Integer accountId);

    /**
     * @description: 查询子字典数据
     * @version <1> 2018-05-18 wl： Created.
     */
    List<PermPerson> queryPerson();

    /**
     * 根据account_id数组查询person表信息
     * @param
     * @return
     * @version <1> 2019/4/19 mason:Created.
     */
    List<PermPerson> findPersonByAccountIdArr(int[] ids);


    /**
     * 保存用户信息
     * @param permAccountMap (company:公司,personName :姓名,accountName 账号,createTime 创建时间,dataStatus 数据状态,delStatus 删除状态)
     * @version <1> 2017-11-02 cxj: Created.
     */
    int savePermPerson(Map<String,Object> permAccountMap );

    /**
     * 根据用户信息Id查询用户信息
     * @param  personId: 用户信息Id
     * @version <1> 2018-05-10 13:46:16 cxw : Created.
     */
    PermPerson findUserById(Integer personId);

    /**
     * 修改用户信息
     * @param  user: 用户信息
     * @version <1> 2018-04-27 13:46:16 Hayden : Created.
     */
    int updateUser(PermPerson user);

    void updateWechatIdByAccountName(PermPerson user);

    /**
     *  @description: 根据用户id集合，查询用户信息列表
     *  用于查询我的关注用户列表信息
     *  @param accIds 用户id集合
     *  @return
     *  @version <1> 2019-04-19 zhangshen： Created.
     */
    List<PermPerson> findPermPersonListByAccIds(@Param("accIds") List<Integer> accIds);

    /**
     * 根据关键词，查找用户名或者备注匹配的用户
     * @param personParam
     * @return 用户列表
     * @version <1> 2019/4/23 mason:Created.
     */
    List<PermPerson> findPersonByKeyword(PersonParam personParam);
}
