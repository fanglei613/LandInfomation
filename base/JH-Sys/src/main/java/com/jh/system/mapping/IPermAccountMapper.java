package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.PermAccount;
import com.jh.system.model.LoginParam;
import com.jh.system.model.UserParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户账户数据访问接口
 * @version <1> 2018-01-11 cxj： Created.
 * @version <2> 2018/1/23 djh： update.
 *      新添根据手机号查询账号列表的业务
 */
@Mapper
public interface IPermAccountMapper extends IBaseMapper<UserParam,PermAccount,Integer> {
    /**
     * @description: 查询用户账户列表数据
     * @version <1> 2018-01-11 cxj： Created.
     */
    List<PermAccount> queryPermAccountList();

    /**
     * @description: 查询用户账户列表数据
     * @version <1> 2018-01-11 cxj： Created.
     */
    List<PermAccount> queryPermAccountAll();
    /**
     * @description: 根据账户名称和密码，查询用户账户信息
     * @version <1> 2018-01-11 cxj： Created.
     */
    PermAccount queryPermAccount(PermAccount permAccount);

    /**
     * @description: 根据登陆账号，检查账号是否存在
     * @param accountName 登陆账号
     * @return PermAccount 系统账户信息
     * @version <1> 2018-01-24 cxj: Created.
     */
    PermAccount checkAccountName(@Param(value = "accountName") String accountName);

    /**
     * @description: 新增账户信息
     * @param permAccount 参数，（账号，密码）
     * @version <1> 2017-11-02 cxj: Created.
     */
    Integer saveAccount(PermAccount permAccount);

    /**
     * @description: 通过账户主键集合，查询账户列表数据
     * @param list 账户主键集合
     * @version <1> 2018-01-25 cxj: Created.
     */
    List<PermAccount> queryAccountsByIdList(List<Integer> list);

    /**
     * 查询指定手机号对应的账号
     *
     * @param accountName 手机号
     * @version <1> 2018/1/23 djh： Created.
     */
    PermAccount queryPermAccountByAccountName(String accountName);


    /**
     * 查询指定accountId对应的账号
     *
     * @param accountId 账户编号
     * @version <1> 2018/3/30 lxy： Created.
     */
    PermAccount getById(Integer accountId);

    /**
     * 更新指定账号的密码
     *
     * @param permAccount 账号
     * @version <1> 2018/1/23 djh： Created.
     */
    int updatePermAccountPassword(PermAccount permAccount);














    /**
     * 新增账户信息
     * @param permAccountEntity 参数，（账号，密码）
     * @version <1> 2017-11-02 cxj: Created.
     */
    Integer saveAccount2(PermAccount permAccount);

    /**
     * 根据条件查询得到一个用户
     * @param params key( serviceKey , phone , delFlag , dataStatus)
     * @return (accountId , accountName ,accountPwd , nickName , serviceKey , personName, sex, personBorn, qq, email, mobile, address,
     *             personType, industry, company)
     * @version <1> 2018-05-10 xzg: Created.
     */
    Map<String,Object> findAccountOne(Map<String,Object> params);

    /**
     * 修改指定账号的密码
     * @param loginEntity 账号
     * @version <1> 2018-05-10 cxw： Created.
     */
    int updatePwdBySms(LoginParam loginEntity);

    void updateServiceKeyByAccountId(PermAccount permAccount);
}
