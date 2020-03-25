package com.jh.collection.utils;

import com.jh.collection.entity.CollectionUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xy 2018-11-23
 */
public class SsoTokenLoginHelper {

    public static final String SSO_SESSIONID = "collection_sessionid";
    /**
     * 采集app用户登录
     *
     * @param sessionId
     * @param collectionUser
     */
    public static void login(String sessionId, CollectionUser collectionUser) {
        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        SsoLoginStore.put(storeKey, collectionUser,sessionId);
    }

    /**
     * 采集app退出
     * @param sessionId
     */
    public static void logout(String sessionId) {
        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        SsoLoginStore.remove(storeKey);
    }

    /**
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(SSO_SESSIONID);
        logout(headerSessionId);
    }


    /**
     * 登录检查
     * @param sessionId
     * @return
     */
    public static CollectionUser loginCheck(String sessionId) {
        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        CollectionUser collectionUser = SsoLoginStore.get(storeKey);
        if (collectionUser != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (collectionUser.getVersion().equals(version)) {
                if ((System.currentTimeMillis() - collectionUser.getExpireFreshTime()) > collectionUser.getExpireMinite() / 2) {
                    collectionUser.setExpireFreshTime(System.currentTimeMillis());
                    SsoLoginStore.put(storeKey, collectionUser,sessionId);
                }
                return collectionUser;
            }
        }
        return null;
    }
    /**
     * login check
     *
     * @param request
     * @return
     */
    public static CollectionUser loginCheck(HttpServletRequest request) {
        String headerSessionId = request.getHeader(SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }


}
