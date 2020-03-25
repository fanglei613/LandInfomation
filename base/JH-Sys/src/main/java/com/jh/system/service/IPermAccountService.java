package com.jh.system.service;

import com.jh.system.base.service.IBaseService;
import com.jh.system.model.UserPwdParam;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermAccount;
import com.jh.system.model.UserParam;
import com.jh.system.model.LoginParam;

import java.util.List;

/**
 * @description: 用户账户业务逻辑接口
 * @version <1> 2018-01-11 cxj： Created.
 */
public interface IPermAccountService extends IBaseService<UserParam, PermAccount, Integer>{
    /**
     * @description: 查询用户账户列表数据
     * @version <1> 2018-01-11 cxj： Created.
     */
//    ResultMessage queryPermAccountList(UserParam userParam);

    ResultMessage queryPermAccountAll();


    /**
     * 根据登陆账号，检查账号是否存在
     * @param loginParam 登陆账号信息
     * @return ResultMessage : 标准的返回信息接口
     * @version <1> 2018-01-24 cxj: Created.
     */
    ResultMessage checkAccountName(LoginParam loginParam);
    /**
     * 保存账户信息
     * @param permAccount 参数，（账号，密码）
     * @version <1> 2017-11-02 cxj: Created.
     */
    Integer saveAccount(PermAccount permAccount);



    /**
     * 更新账号
     *
     * @param permAccount 账号
     * @return
     * @version <1> 2018/1/23 djh： Created.
     */
    ResultMessage updatePermAccount(PermAccount permAccount);



    PermAccount queryPermAccountByAccountId(Integer accountId);

    /**
     * Description: 根据accountId重置密码
     * @param pwdParam
     * @return
     * @version <1> 2018/8/3 16:42 zhangshen: Created.
     */
    ResultMessage resetUserPwd(UserPwdParam pwdParam);

    /**
     * 检查账号名(accountCode)是否已注册
     * @param accountId
     * @param accountName
     * @return
     * @version <1> 2018-08-06 zhangshen： Created.
     */
    ResultMessage checkAccountCodeExists(Integer accountId, String accountName);


    /**
     * 根据accountId更新service_key
     * @param permAccount
     * @return
     */
    ResultMessage updateServiceKeyByAccountId(PermAccount permAccount);

    /**
     * 根据accountName查询用户
     * @param accountName
     * @return
     */
    ResultMessage queryPermAccountByAccountName(String accountName);
}
