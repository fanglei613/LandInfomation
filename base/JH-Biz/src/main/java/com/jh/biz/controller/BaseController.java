/**
* 控制层基类，提供控制层相关方法，如：
* 
* @version Hayden 2018-08-08 13:50:48 : Created.
*/

package com.jh.biz.controller;

import com.jh.util.AccountTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class BaseController{
	@Autowired	
    public HttpServletRequest request;
	@Autowired	
    public HttpServletResponse response;

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
    public Map<String,Object> getCurrentPermAccount(){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);

        return userInfo;
    }


    /**
     * @description: 得到当前登录用户对象
     */
    public Map getCurrentUserInfo(HttpServletRequest request){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserInfoFromRedis(token);

        return userInfo;
    }

    /**
     * 获取当前登录用户id
     * @param request
     * @return
     */
    public Long getCurrentAccountId(HttpServletRequest request){
        Long accountId = null;
        Map<String,Object> currentAccount = getCurrentUserInfo(request);
        if (currentAccount != null && currentAccount.containsKey("accountId")){
            accountId = Long.parseLong(currentAccount.get("accountId").toString());
        }
        return accountId;
    }

    /**
     * 获取当前登录用户regionId
     * @param request
     * @return
     */
    public Long getCurrentRegionId(HttpServletRequest request){
        Long regionId = null;
        Map<String,Object> currentAccount = getCurrentUserInfo(request);
        if (currentAccount != null && currentAccount.containsKey("region_id")){
            regionId = Long.parseLong(currentAccount.get("region_id").toString());
        }
        return regionId;
    }

    /**
     * @description: 得到当前登录用户对象
     */
    public Map getCurrentUserData(HttpServletRequest request){
        String token = AccountTokenUtil.getToken(request);
        Map<String,Object> userInfo = AccountTokenUtil.getUserDataFromRedis(token);

        return userInfo;
    }

    /**
     * 获取当前登录人ID
     * @return
     */
    public Integer getCurrentAccountId(){
        Map<String,Object> userInfo = getCurrentPermAccount();
        Integer accountId = null;
        if(userInfo != null && userInfo.size() > 0){
            accountId = (Integer)userInfo.get("accountId");
        }
        return accountId;
    }

    /**
     * 获取当前登录人名称
     * @return
     */
    public String getCurrentNickName(){
        Map<String,Object> userInfo = getCurrentPermAccount();
        String accountName = null;
        if(userInfo != null && userInfo.size() > 0){
            accountName = ("null".equals(userInfo.get("nickName").toString())|| StringUtils.isBlank(userInfo.get("nickName").toString())) ? userInfo.get("accountName")+"" : userInfo.get("nickName") +"";
        }
        return accountName;
    }
}