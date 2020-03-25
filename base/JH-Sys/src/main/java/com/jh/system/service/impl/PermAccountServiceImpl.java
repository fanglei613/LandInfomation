package com.jh.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.jh.enums.IsEnum;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.system.model.UserPwdParam;
import com.jh.system.util.RandomUtils;
import com.jh.util.Base64Util;
import com.jh.util.MD5Util;
import com.jh.util.PropertyUtil;
import com.jh.util.StringUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.Enum.UserEnum;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import com.jh.system.entity.PermResource;
import com.jh.system.entity.PermRole;
import com.jh.system.mapping.IPermAccountMapper;
import com.jh.system.mapping.IPermPersonMapper;
import com.jh.system.mapping.IPermRoleMapper;
import com.jh.system.model.UserParam;
import com.jh.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jh.system.Enum.PersonTypeEnum;
import com.jh.system.model.LoginParam;

import java.time.LocalDateTime;
import java.util.List;

import static com.jh.system.util.RandomUtils.USER_TOTAL_LENGTH;
import static com.netflix.appinfo.AmazonInfo.MetaDataKey.accountId;

@Transactional
@Service
public class PermAccountServiceImpl extends BaseServiceImpl<UserParam, PermAccount, Integer> implements IPermAccountService {
    private Logger logger = Logger.getLogger(PermAccountServiceImpl.class);

    @Autowired
    private IPermAccountMapper permAccountMapper;

    @Autowired
    private IPermPersonMapper permPersonMapper;
    @Autowired
    private IRelateAccountRoleService relateAccountRole;

    @Autowired
    private IUserService userService;


//    public ResultMessage queryPermAccountList(UserParam userParam) {
//        try {
//            PageHelper.startPage(userParam.getPage(),userParam.getRows());
//            List<PermAccount> list = permAccountMapper.queryPermAccountList();
//            return ResultMessage.success(list);
//        }catch (Exception e){
//            logger.error("PermAccountService queryPermAccountList method :" + e.getMessage());
//            return ResultMessage.fail();
//        }
//    }

    @Override
    public ResultMessage queryPermAccountAll() {
        List<PermAccount> list=permAccountMapper.queryPermAccountAll();
        return ResultMessage.success(list);
    }

    public ResultMessage checkAccountName(LoginParam loginParam ) {
        String accountName = loginParam.getAccountName();
        if(StringUtils.isBlank(accountName)){
            return ResultMessage.fail(UserEnum.ACCOUNT_NAME_EMPTY.getValue(), UserEnum.ACCOUNT_NAME_EMPTY.getMesasge());
        }
        PermAccount permAccount = permAccountMapper.checkAccountName(accountName);
        if(permAccount == null){
            return ResultMessage.fail(UserEnum.ACCOUNT_NAME_NOT_EXIST.getValue(), UserEnum.ACCOUNT_NAME_NOT_EXIST.getMesasge());
        }else{
            return ResultMessage.success(UserEnum.ACCOUNT_NAME_EXIST.getValue(), UserEnum.ACCOUNT_NAME_EXIST.getMesasge());
        }
    }

    public Integer saveAccount(PermAccount permAccount){
         Integer num = permAccountMapper.saveAccount(permAccount);
        //修改昵称 注册时随机生产昵称：用户_accountId+N位随机字符串
        if(permAccount.getAccountId() != null && StringUtils.isBlank(permAccount.getNickName())){
            String accountIdStr = permAccount.getAccountId().toString();
            //随机数 总位数 - 主键长度
            Integer accountLen = accountIdStr.length();
            Integer totalLen = RandomUtils.USER_TOTAL_LENGTH;
            Integer randomStr = accountLen >= totalLen ? 0 : totalLen - accountIdStr.length();
            String nickName = RandomUtils.USER_NAME + accountIdStr;
            if(randomStr > 0){
                nickName = nickName  + RandomUtils.randomString(randomStr);
            }
            permAccount.setNickName(nickName);
            logger.info("用户注册生成的昵称为：" + nickName );
            permAccountMapper.update(permAccount);
        }

        return num;
    }

    public List<PermAccount> queryAccountsByIdList(List<Integer> list) {
        return permAccountMapper.queryAccountsByIdList(list);
    }

    @Override
    protected IBaseMapper<UserParam, PermAccount, Integer> getDao() {
        return permAccountMapper;
    }

    /**
     * 查询指定手机号对应的账号列表
     *
     * @param accountName 手机号
     * @version <1> 2018/1/23 djh： Created.
     */
    @Override
    public ResultMessage queryPermAccountByAccountName(String accountName) {
        if (StringUtils.isBlank(accountName)) {
            return ResultMessage.fail(UserEnum.ACCOUNT_NAME_NOT_EXIST.getValue(), UserEnum.ACCOUNT_NAME_NOT_EXIST.getMesasge());
        }
        PermAccount ua = permAccountMapper.queryPermAccountByAccountName(accountName);
        if(ua != null){
            return ResultMessage.success(ua);
        }
        return ResultMessage.fail(UserEnum.ACCOUNT_NAME_NOT_EXIST.getValue(), UserEnum.ACCOUNT_NAME_NOT_EXIST.getMesasge());
    }

    /**
     * 更新账号
     *
     * @param permAccount 账号
     * @return
     * @version <1> 2018/1/23 djh： Created.
     */
    public ResultMessage updatePermAccount(PermAccount permAccount) {
        String pwd = new String(Base64Util.decode(permAccount.getAccountPwd()));
        permAccount.setAccountPwd(MD5Util.digest(pwd));
        int i = permAccountMapper.updatePermAccountPassword(permAccount);
        ResultMessage result = new ResultMessage();
        boolean flag = i > 0;
        result.setFlag(flag);
        result.setCode(flag ? IsEnum.YES.getValue() : IsEnum.NO.getValue());
        result.setMsg(flag ? IsEnum.YES.getText() : IsEnum.NO.getText());
        return result;
    }

    /**
     * 更新账号
     *
     * @param permAccount 账号
     * @param personType 用户类型
     * @return
     * @version <1> 2018/1/23 djh： Created.
     */
    public ResultMessage saveUser(PermAccount permAccount, Long personType) {
        ResultMessage result = ResultMessage.fail("用户信息新增失败");
        if (permAccount != null && !StringUtils.isEmpty(permAccount.getAccountName())){
            String pwd = new String(Base64Util.decode(permAccount.getAccountPwd()));
            permAccount.setAccountPwd(MD5Util.digest(pwd));
            permAccount.setCreateTime(LocalDateTime.now());
            permAccountMapper.saveAccount(permAccount);

            Integer permAccountId = permAccount.getAccountId();
            PermPerson permPerson = new PermPerson();
            permPerson.setAccountId(permAccountId);
            permPerson.setMobile(permAccount.getAccountName());
            permPerson.setPersonType(PersonTypeEnum.PERSON_TYPE_OUTER.getId());
            permPerson.setPersonName(permAccount.getNickName());
            permPerson.setCompany(permAccount.getPermPerson().getCompany());

            permPersonMapper.save(permPerson);
            //分配默认权限
            relateAccountRole.setDefaultPermission(permAccountId);

            //保存默认数据访问权限
            ResultMessage resData = userService.saveDeafultDataPermisson(permAccountId);

            if(resData.isFlag()){
                result = ResultMessage.success("用户信息新增成功");
            }

//            result.setCode(IsEnum.YES.getValue());
//            result.setMsg(IsEnum.YES.getText());
        } else {
//            result.setCode(IsEnum.Error.getValue());
//            result.setMsg(IsEnum.Error.getText());
        }

        return result;
    }

    @Override
    public PermAccount queryPermAccountByAccountId(Integer accountId) {
        PermAccount ua = permAccountMapper.getById(accountId);
        return ua;
    }

    /**
     * Description: 根据accountId重置密码
     * @param pwdParam
     * @return
     * @version <1> 2018/8/3 16:42 zhangshen: Created.
     */
    @Override
    public ResultMessage resetUserPwd(UserPwdParam pwdParam) {
        if (pwdParam.getAccountId() == null) {
            return ResultMessage.fail("accountId为空");
        }
        PermAccount permAccount = new PermAccount();
        permAccount.setAccountId(pwdParam.getAccountId());
        permAccount.setAccountPwd(MD5Util.digest(PropertyUtil.getPropertiesForConfig("LOGIN_DEFAULT_PASSWORD")));
        permAccountMapper.updatePermAccountPassword(permAccount);

        return ResultMessage.success("密码已重置为" + PropertyUtil.getPropertiesForConfig("LOGIN_DEFAULT_PASSWORD"));
    }

    /**
     * 检查账号名(accountCode)是否已注册
     * @param accountId
     * @param accountName
     * @return
     * @version <1> 2018-08-06 zhangshen： Created.
     */
    @Override
    public ResultMessage checkAccountCodeExists(Integer accountId, String accountName) {
        PermAccount permAccount = permAccountMapper.checkAccountName(accountName);
        if (accountId == null) { //新增
            if (permAccount == null) {
                return ResultMessage.success("新增时, 账号名不存在", true);
            }
            return ResultMessage.success("新增时, 账号名存在", false);
        } else { //修改
            if (permAccount == null) {
                return ResultMessage.success("修改时, 账号名不存在", true);
            } else if(permAccount != null && accountId.equals(permAccount.getAccountId())) {
                return ResultMessage.success("修改时, 账号名没更改", true);
            }
            return ResultMessage.success("修改时, 账号名存在", false);
        }
    }


    @Override
    public ResultMessage updateServiceKeyByAccountId(PermAccount permAccount) {

        permAccountMapper.updateServiceKeyByAccountId(permAccount);

        return ResultMessage.success();
    }
}
