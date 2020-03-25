package com.jh.system.base.controller;

import com.jh.system.entity.PermAccount;
import com.jh.system.entity.PermPerson;
import com.jh.system.entity.PermRole;
import com.jh.system.Enum.PersonTypeEnum;
import com.jh.system.service.IUserService;
import com.jh.util.AccountTokenUtil;
import com.jh.util.JsonUtils;
import com.jh.util.RedisUtil;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 操作接口基类
 * @version<1> 2018-01-17 cxj: Created.
 */
public class BaseController {


    @Autowired
    public HttpServletRequest request;

    @Autowired
    public IUserService userService;


    /**
     * 获取当前token
     * @return
     * @version<1> 2018-07-19 lcw :Created.
     */
    public String getToken(){
        return AccountTokenUtil.getToken(request);
    }

    /**
     * @description: 得到当前用户对象
     * @version <1> 2018-01-17 cxj： Created.
     */
    public PermAccount getCurrentPermAccount(){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);

        PermAccount permAccount = new PermAccount();
        permAccount.setAccountId((Integer) userInfo.get("accountId"));
        permAccount.setAccountName((userInfo.get("nickName") == null || userInfo.get("nickName") == "") ? userInfo.get("accountName")+"" : userInfo.get("nickName") +"");

        PermPerson permPerson = new PermPerson();

        if (!JSONNull.getInstance().equals(userInfo.get("personType"))) permPerson.setPersonType((Integer)userInfo.get("personType"));
        permPerson.setPersonId((Integer) userInfo.get("personId"));

        permAccount.setPermPerson(permPerson);



        return permAccount;
    }

    /**
     * 获取用户类型
     * @param permAccount
     * @return
     * @version<1> 2018-03-15 lcw :Created.
     */
    public int getPersonType(PermAccount permAccount){

        int personType = PersonTypeEnum.PERSON_TYPE_INNER.getId();
        if(permAccount == null){
            return personType;
        }
        return permAccount.getPermPerson().getPersonType();
    }

    /**
     * 获取用户角色信息
     * @param permAccount
     * @return
     * @version<1> 2018-03-18 lcw :created.
     */
    public List<PermRole> getRoles(PermAccount permAccount){
        if(permAccount != null){
            return permAccount.getRoleList();
        }
        return null;
    }

    /**
     * 获取用户角色信息
     * @param request
     * @return
     * @version<1> 2018-03-18 lcw :created.
     */
    public List<PermRole> getRoles(HttpServletRequest request){
        PermAccount permAccount = getCurrentPermAccount();
        if(permAccount != null){
            return permAccount.getRoleList();
        }
        return null;
    }

    /**
     * 判断用户角色是否包含管理员角色(包括系统管理员和超级管理员)
     * @param permAccount
     * @return
     * @version<1> 2018-03-18 lcw :created.
     *
     */
//    public boolean checkRoleAdmin(PermAccount permAccount){
//        boolean bool = false;
//        if(permAccount != null){
//            List<PermRole> list=  permAccount.getRoleList();
//            if(list != null && list.size() > 0){
//                for(PermRole role : list){
//                    if(RoleTypeEnum.ROLE_TYPE_ADMIN.getId().equals(role.getRoleType())){
//                        bool = true;
//                        break;
//                    }
//                }
//            }
//        }
//        return bool;
//    }


    /**
     * 判断用户角色是否包含管理员角色(包括系统管理员和超级管理员)
     * @param request
     * @return
     * @version<1> 2018-03-18 lcw :created.
     */
//    public boolean checkRoleAdmin(HttpServletRequest request){
//        PermAccount permAccount = getCurrentPermAccount(request);
//        return checkRoleAdmin(permAccount);
//    }

    /**
     * 移动端通过用户phone,获取用户信息
     * @param
     * @return
     * @version <1> 2019/4/10 14:51 zhangshen:Created.
     */
    public Map<String,Object> getUserInfoApp(String phone) {
        Map<String,Object> userInfo = new HashMap<String,Object>();
        ResultMessage resultMessage = userService.findUserInfoByPhone(phone);
        userInfo = (Map<String,Object>) resultMessage.getData();
        return userInfo;
    }

    /**
     * 移动端获取当前登录人ID
     * @return
     */
    public Integer getCurrentAccountIdApp(String phone){
        Map<String,Object> userInfo = getUserInfoApp(phone);
        Integer accountId = null;
        if(userInfo != null && userInfo.size() > 0){
            accountId = (Integer)userInfo.get("accountId");
        }
        return accountId;
    }
}
