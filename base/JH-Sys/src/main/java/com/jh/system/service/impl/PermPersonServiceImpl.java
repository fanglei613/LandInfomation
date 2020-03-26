package com.jh.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.cache.service.ICacheService;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.system.service.IUserService;
import com.jh.util.MD5Util;
import com.jh.util.PropertyUtil;
import com.jh.util.cache.IdTransformUtils;
import com.jh.util.ceph.CephUtils;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import com.jh.system.entity.RelateAccountRole;
import com.jh.system.mapping.IPermPersonMapper;
import com.jh.system.model.PersonParam;
import com.jh.system.service.IPermAccountService;
import com.jh.system.service.IPermPersonService;
import com.jh.system.service.IRelateAccountRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息实现类
 * @version <1> 2018-01-23 lcw : Created.
 */
@Service
@Transactional
public class PermPersonServiceImpl extends BaseServiceImpl<PersonParam, PermPerson , Integer> implements IPermPersonService {
    private Logger logger = Logger.getLogger(PermPersonServiceImpl.class);

    @Autowired
    private IPermPersonMapper permPersonMapper;
    @Autowired
    private IPermAccountService permAccountService;

    @Autowired
    private IRelateAccountRoleService relateAccountRoleService;


    @Autowired
    private IUserService userService;

    @Autowired
    private ICacheService cacheService;

    @Override
    protected IBaseMapper<PersonParam, PermPerson, Integer> getDao() {
        return permPersonMapper;
    }

    public PageInfo<PermPerson> findByPage(PersonParam personParam) {
        PageHelper.startPage(personParam.getPage(), personParam.getRows());
        PageHelper.orderBy(personParam.getSidx() + " " + personParam.getSord());
        List<PermPerson> list =  permPersonMapper.findByPage(personParam);
        return new PageInfo<PermPerson>(list);
    }

    public ResultMessage saveRelateRole(PersonParam personParam) {
        permPersonMapper.unRelateRole(personParam.getAccountId());

        if(personParam != null && personParam.getRoleIds() !=null){
            for(int i = 0 ; i < personParam.getRoleIds().length; i++){
                RelateAccountRole relateAccountRole = new RelateAccountRole();
                relateAccountRole.setAccountId(personParam.getAccountId());
                relateAccountRole.setRoleId(Integer.parseInt(personParam.getRoleIds()[i]));
                permPersonMapper.relateRole(relateAccountRole);
            }
        }

        return ResultMessage.success();
    }

    public ResultMessage savePersonAndAccount(PersonParam personParam) {
        PermPerson permPerson = personParam.getPermPerson();
        PermAccount permAccount = personParam.getPermAccount();
        permAccount.setCreator(permPerson.getCreator());
        permAccount.setCreatorName(permPerson.getCreatorName());
        permAccount.setAccountPwd(MD5Util.digest(PropertyUtil.getPropertiesForConfig("LOGIN_DEFAULT_PASSWORD")));
        permAccount.setDataStatus(permPerson.getDataStatus());
        permAccount.setAccountCode("".equals(permAccount.getAccountCode()) ? null : permAccount.getAccountCode());
        permAccount.setAccountName("".equals(permAccount.getAccountName()) ? null : permAccount.getAccountName());
        permAccountService.saveAccount(permAccount);
        Integer accountId = permAccount.getAccountId();
        personParam.getPermPerson().setAccountId(accountId);
        permPersonMapper.save(personParam.getPermPerson());

        //刷新用户缓存
        cacheService.updateCacheUser(accountId);

        relateAccountRoleService.setDefaultPermission(accountId);


        //保存默认数据访问权限
        ResultMessage resData = userService.saveDeafultDataPermisson(accountId);

        if(resData.isFlag()){
            return ResultMessage.success("用户信息新增成功",accountId);
        }else{

            return ResultMessage.success("用户信息新增失败");
        }

    }

    public ResultMessage findPersonAndAccount(Integer personId) {
        ResultMessage result = this.getById(personId);
        PermPerson permPerson = (PermPerson) result.getData();
        result = permAccountService.getById(permPerson.getAccountId());
        PermAccount permAccount = (PermAccount) result.getData();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("permPerson",permPerson);
        map.put("permAccount",permAccount);
        return ResultMessage.success(map);
    }


    public ResultMessage save(PersonParam personParam) {
        PermPerson permPerson = personParam.getPermPerson();
        PermAccount permAccount = personParam.getPermAccount();
        permAccount.setAccountName("".equals(permPerson.getMobile()) ? null : permPerson.getMobile());

        permAccount.setNickName(permAccount.getNickName());
        permAccount.setModifier(permPerson.getModifier());
        permAccount.setModifierName(permPerson.getModifierName());
        permPerson.setAccountId(permAccount.getAccountId());
        permPerson.setPersonType(permPerson.getPersonType());
        permAccount.setDataStatus(permPerson.getDataStatus());
        permAccount.setAccountCode("".equals(permAccount.getAccountCode()) ? null : permAccount.getAccountCode());
        permAccountService.update(permAccount);
        permPersonMapper.update(personParam.getPermPerson());
        relateAccountRoleService.setDefaultPermission(permAccount.getAccountId());
        //刷新用户缓存
        cacheService.updateCacheUser(permAccount.getAccountId());
        return ResultMessage.success();
    }

    public ResultMessage updatePersonDataStatus(PermPerson person) {
        ResultMessage result = ResultMessage.fail();
        permPersonMapper.update(person);
        PermAccount permAccount = new PermAccount();
        permAccount.setAccountId(person.getAccountId());
        permAccount.setModifier(person.getModifier());
        permAccount.setModifierName(person.getModifierName());
        permAccount.setDataStatus(person.getDataStatus());
        permAccountService.update(permAccount);
        result = ResultMessage.success();
        return result;
    }

    @Override
    public PermPerson queryByAccountName(String accountName) {
        return permPersonMapper.queryByAccountName(accountName);
    }

    /**
     * 根据账号ID查询用户类型
     * @param acountId 账号ID
     * @return personType 用户类型
     * @version <1> 2018-03-21 cxw： Created.
     */
    @Override
    public Integer queryPersonType(Integer acountId) {
        return permPersonMapper.queryPersonType(acountId);
    }

    @Override
    public ResultMessage findPersonByAccountId(Integer accountId) {
        try {
            PermPerson permPerson = permPersonMapper.findPersonByAccountId(accountId);
            return ResultMessage.success(permPerson);
        }catch (Exception e){
            return ResultMessage.fail();
        }
    }

    @Override
    public ResultMessage queryPerson() {
        List<PermPerson> list = permPersonMapper.queryPerson();
        return ResultMessage.success(list);
    }

    /**
     * 更换用户头像
     * @param person 用户信息
     * @return
     * @version <1> 2019/3/28 mason:Created.
     */
    @Override
    public ResultMessage editPersonPhoto(HttpServletRequest request, PermPerson person) {
        //调用公共方法获取照片存储路径
        String photoUrl = CephUtils.uploadImage(request,person.getAccountId());
        person.setPhotoUrl(photoUrl);
        //更新person表数据
        permPersonMapper.update(person);
        //刷新用户缓存
        cacheService.updateCacheUser(person.getAccountId());
        return ResultMessage.success(photoUrl);
    }

    /**
     *  @description: 根据用户id集合，查询用户信息列表
     *  用于查询我的关注用户列表信息
     *  @param accIds 用户id集合
     *  @return
     *  @version <1> 2019-04-19 zhangshen： Created.
     */
    @Override
    public ResultMessage findPermPersonListByAccIds(List<Integer> accIds) {
        List<PermPerson> list = permPersonMapper.findPermPersonListByAccIds(accIds);
        IdTransformUtils.idTransForList(list);
        return ResultMessage.success(list);
    }
    /**
     * 根据account_id数组查询person表信息
     * @param ids
     * @return
     * @version <1> 2019/4/19 mason:Created.
     */
    @Override
    public ResultMessage batchFindPersonByAccountIdArr(int[] ids) {
        List<PermPerson> list= permPersonMapper.findPersonByAccountIdArr(ids);
        return ResultMessage.success(list);
    }

    /**
     * 根据关键词，查找用户名或者备注匹配的用户
     * @param personParam
     * @return 用户列表
     * @version <1> 2019/4/23 mason:Created.
     */
    @Override
    public PageInfo<PermPerson> findPersonByKeyword(PersonParam personParam) {
        PageHelper.startPage(personParam.getPage(), personParam.getRows());
        List<PermPerson> list =  permPersonMapper.findPersonByKeyword(personParam);
        return new PageInfo<PermPerson>(list);
    }
}
