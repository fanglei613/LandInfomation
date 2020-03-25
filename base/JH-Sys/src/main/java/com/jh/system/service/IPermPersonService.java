package com.jh.system.service;

import com.github.pagehelper.PageInfo;
import com.jh.system.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermPerson;
import com.jh.system.model.PersonParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 用户信息接口
 * @version <1> 2018-01-23 lcw： Created.
 */
public interface IPermPersonService extends IBaseService<PersonParam, PermPerson, Integer> {

    /**
     * 人员信息分页查询
     * @param personParam
     * @return
     * @version <1> 2018-01-25 cxj： Created.
     */
    public PageInfo<PermPerson> findByPage(PersonParam personParam);

    /**
     * 用户关联角色
     * @param personParam
     * @return
     */
    public ResultMessage saveRelateRole(PersonParam personParam);

    /**
     * 新增用户信息和账号信息
     * 同时赋予默认数据访问权限
     * @param personParam
     * @return
     * @version <1> 2017-12-17 lcw： Created.
     */
    ResultMessage savePersonAndAccount(PersonParam personParam);

    /**
     * 查询用户账户和用户基本信息
     * @param personId 用户基本信息主键
     * @return ResultMessage
     * @version <1> 2018-01-25 cxj： Created.
     */
    ResultMessage findPersonAndAccount(Integer personId);

    /**
     * 用户更新
     * @param personParam
     * @return
     * @version <1> 2018-01-25 cxj : created.
     */
    ResultMessage save(PersonParam personParam);

    /**
     * 禁用/启用用户
     * @param person
     * @return
     * @version <1> 2018-01-25 cxj : created.
     */
    ResultMessage updatePersonDataStatus(PermPerson person);
    /**
     * 根据账号查询用户信息
     * @param accountName
     * @return
     * @version<1> 2018-03-09 lcw : Created.
     */
    PermPerson queryByAccountName(String accountName);

    /**
     * 根据账号ID查询用户类型
     * @param acountId 账号ID
     * @return personType 用户类型
     * @version <1> 2018-03-21 cxw： Created.
     */
    Integer queryPersonType(Integer acountId);

    /*
     * 功能描述:查询vip类型
     * @Param:
     * @Return:
     * @version<1>  2020/3/2  wangli :Created
     */
    Integer queryVipType(Integer acountId);
    /**
     * 根据账号ID查询用户信息
     * @param accountId
     * @version <1> 2018-03-21 cxj： Created.
     */
    ResultMessage findPersonByAccountId(Integer accountId);

    /**
     * @description: 查询系统用户
     * @param
     * @version <1> 2018-05-1 wl： Created.
     */
    ResultMessage queryPerson();

    /**
     * 更换用户头像
     * @param person 用户信息
     * @return
     * @version <1> 2019/3/28 mason:Created.
     */
    ResultMessage editPersonPhoto(HttpServletRequest request,PermPerson person);

    /**
     * 根据account_id数组查询person表信息
     * @param ids
     * @return
     * @version <1> 2019/4/19 mason:Created.
     */
    ResultMessage batchFindPersonByAccountIdArr(int[] ids);

    /**
     *  @description: 根据用户id集合，查询用户信息列表
     *  用于查询我的关注用户列表信息
     *  @param accIds 用户id集合
     *  @return
     *  @version <1> 2019-04-19 zhangshen： Created.
     */
    ResultMessage findPermPersonListByAccIds(List<Integer> accIds);

    /**
     * 根据关键词，查找用户名或者备注匹配的用户
     * @param personParam
     * @return 用户列表
     * @version <1> 2019/4/23 mason:Created.
     */
    PageInfo<PermPerson> findPersonByKeyword(PersonParam personParam);

}
