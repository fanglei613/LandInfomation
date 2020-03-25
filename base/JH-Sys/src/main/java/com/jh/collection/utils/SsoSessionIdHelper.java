package com.jh.collection.utils;


import com.jh.collection.entity.CollectionUser;

public class SsoSessionIdHelper {

    /**
     * 创建sessionId
     * @param collectionUser
     * @return
     */
    public static String makeSessionId(CollectionUser collectionUser) {
        String sessionId = collectionUser.getPhone().concat("_").concat(collectionUser.getVersion());
        return sessionId;
    }

    /**
     * 解析sessionId key
     * @param sessionId
     * @return
     */
    public static String parseStoreKey(String sessionId) {
        if (sessionId != null && sessionId.indexOf("_") > -1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length == 2
                    && sessionIdArr[0] != null
                    && sessionIdArr[0].trim().length() > 0) {
                String userId = sessionIdArr[0].trim();
                return userId;
            }
        }
        return null;
    }

    /**
     * 解析sessionId
     * @param sessionId
     * @return
     */
    public static String parseVersion(String sessionId) {
        if (sessionId != null && sessionId.indexOf("_") > -1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length == 2
                    && sessionIdArr[1] != null
                    && sessionIdArr[1].trim().length() > 0) {
                String version = sessionIdArr[1].trim();
                return version;
            }
        }
        return null;
    }

}
