package com.jh.collection.utils;

import com.jh.collection.entity.CollectionUser;
import com.jh.util.JsonUtils;
import com.jh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;

public class SsoLoginStore {

    private static int redisExpireMinite = 1440;    // 1440 minite, 24 hour
    public static final String SSO_SESSIONID = "collection_app_sessionid";

    public static void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }
    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     * @param storeKey
     * @return
     */
    public static CollectionUser get(String storeKey) {

        String redisKey = redisKey(storeKey);
        String value = RedisUtil.get("redisKey");
        if(StringUtils.isNotEmpty(value)){
            CollectionUser collectionUser = JsonUtils.jsonToPojo(value,CollectionUser.class);
            return collectionUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        RedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param collectionUser
     */
    public static void put(String storeKey, CollectionUser collectionUser, String sessionId) {
        RedisUtil.set(sessionId, JsonUtils.objectToJson(collectionUser), redisExpireMinite * 60);  // 网关访问token
        String redisKey = redisKey(storeKey);
        RedisUtil.set(redisKey, JsonUtils.objectToJson(collectionUser), redisExpireMinite * 60);  // minite to second
    }

    private static String redisKey(String sessionId){
        return SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
