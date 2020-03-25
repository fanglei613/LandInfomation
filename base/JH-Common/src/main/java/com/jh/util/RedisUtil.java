/**
* Redis工具类,Redis数据类型：
* 1. String	：普通的key/value存储
* 2. List	：适用于列表式存储且顺序相对比较固定，例如：省份、城市列表
* 3. Set 	：对外提供的功能与list类似,当需要存储一个列表数据,又不希望出现重复数据时,可选用set
* 4. hash	：类似于表记录的存储，页面视图所需数据的存储
* 5. ZSet	：zset的使用场景与set类似,区别是set不是自动有序的,而zset可以通过用户额外提供一个优先级(score)的参数来为成员排序,并且是插入有序的,即自动排序.当你需要一个有序的并且不重复的集合列表,那么可以选择zset数据结构。
* @version <1> 2018-04-28 17:02:35 Hayden : Created.
*/
package com.jh.util;

import com.jh.constant.PropertiesConstant;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisUtil{
	private static JedisPool jedisPool = null;
	private static RedisUtil redisUtil = new RedisUtil();


	/**
	* Redis工具类中的回调接口，主要用于切片处理。
	* @version <1> 2018-04-28 17:08:52 Hayden : Created.
	*/
	interface RedisCallback<T>{
		public T call(Jedis jedis,Object param);
	}

	private RedisUtil(){
		if(jedisPool == null){
			String 	ipAddress = PropertyUtil.getPropertiesForConfig("redis_id", PropertiesConstant.REDIS_CONFIG);
			Integer port = Integer.valueOf(PropertyUtil.getPropertiesForConfig("redis_port",PropertiesConstant.REDIS_CONFIG));
			String passwd = PropertyUtil.getPropertiesForConfig("redis_passwd",PropertiesConstant.REDIS_CONFIG);
			Integer timeOut = Integer.valueOf(PropertyUtil.getPropertiesForConfig("redis_timeout",PropertiesConstant.REDIS_CONFIG));
			Integer maxTotal = Integer.valueOf(PropertyUtil.getPropertiesForConfig("redis_maxTotal",PropertiesConstant.REDIS_CONFIG));
			Integer maxIdle = Integer.valueOf(PropertyUtil.getPropertiesForConfig("redis_maxIdle",PropertiesConstant.REDIS_CONFIG));
			Long 	maxWaitMillis = Long.valueOf(PropertyUtil.getPropertiesForConfig("redis_maxWaitMillis",PropertiesConstant.REDIS_CONFIG));
			Boolean testOnBorrow = Boolean.valueOf(PropertyUtil.getPropertiesForConfig("redis_testOnBorrow",PropertiesConstant.REDIS_CONFIG));
			Boolean testOnReturn = Boolean.valueOf(PropertyUtil.getPropertiesForConfig("redis_testOnReturn",PropertiesConstant.REDIS_CONFIG));

			// 设置Redis池配置
			JedisPoolConfig config = new JedisPoolConfig();
			config.setTestWhileIdle(false);
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWaitMillis);
			config.setTestOnBorrow(testOnBorrow);
			config.setTestOnReturn(testOnReturn);

			jedisPool = new JedisPool(config,ipAddress,port,timeOut,passwd);
		}
	}

	/**
     * 获取Jedis实例
     * @version <1> 2018-04-28 17:55:21 Hayden : Created.
     */
    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool != null){
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();;
            return null;
        }
    }

    /**
    * 采用回调的方式执行Redis命令。
    * @param  callback 	: 回调接口
    * @param  param			: 执行参数
   	* @version <1> 2018-04-28 17:58:03 Hayden : Created.
   	*/
    private static  <T> T execute(RedisCallback<T> callback,Object... param){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return callback.call(jedis, param);
        } catch (JedisConnectionException e) {
            if (jedis != null){
                jedis.close();
            }
        } finally {
        	if (jedis != null){
				jedis.close();
			}
		}
		return null;
    }

    /**
	 * 更具key 删除
	 * @param key
	 */
	public static Long del(String key) {
		return execute(new RedisCallback<Long>() {
			public Long call(Jedis jedis, Object parms) {
				String key = ((Object[]) parms)[0].toString();
				return jedis.del(key);
			}
		}, key);
	}

	/**
	 * 判断key 是否存在
	 * @param key
	 * @return
     */
	public static Boolean testKeyExists(String key){
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean call(Jedis jedis, Object param) {
				String key = ((Object[]) param)[0].toString();
				return jedis.exists(key);
			}
		},key);
	}

    //=======================================HashMap=======================================//
	/**
	 * 查看哈希表 key 中，给定域 attributeKey 是否存在
	 * @param redisKey
	 * @param attributeKey
	 * @return true or false
	 */
	public static Boolean hexists(String redisKey,String attributeKey){
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean call(Jedis jedis, Object param) {
				String key = ((Object[]) param)[0].toString();
				String field = ((Object[]) param)[1].toString();
				return jedis.hexists(key,field);
			}
		},redisKey,attributeKey);
	}

	/**
	 * 哈希表 key 中给定域 attributeKey 的值
	 * @param key
	 * @param attributeKey
	 * @return key 对应的 hash  ,attributeKey 对应的属性值
	 */
	public static String hget(String key, String attributeKey) {
		return execute(new RedisCallback<String>() {
			public String call(Jedis jedis,Object params) {
				Object[] paramObjArray = (Object[]) params;
				String key = paramObjArray[0]!=null ?paramObjArray[0].toString():null;
				String field = paramObjArray[1]!=null ?paramObjArray[1].toString():null;
				if(field==null){
					return null;
				}
				return jedis.hget(key, field);
			}
		}, key, attributeKey);
	}

	/**
	 * 哈希表 key 中，所有的域和值
	 * @param key
	 * @return  Map<String, String>  ，key  对应的 hash ,所有key--value 的键值对
	 */
	public static Map<String, String> hgetAll(String key) {
		return execute(new RedisCallback<Map<String, String>>() {
			@Override
			public Map<String, String> call(Jedis jedis, Object params) {
				String key = ((Object[]) params)[0].toString();
				return jedis.hgetAll(key);
			}
		},key);
	}

	/**
	 * 删除哈希表 key 中的 attributeKey 对应的值
	 * @param mapKey
	 * @param attributeKey
	 * @return
	 */
	public static Long hdel(String mapKey, String attributeKey){
		return execute(new RedisCallback<Long>() {
			@Override
			public Long call(Jedis jedis, Object params) {
				String key = ((Object[]) params)[0].toString();
				String field = ((Object[]) params)[1].toString();
				return jedis.hdel(key, field);
			}
		}, mapKey,attributeKey);
	}


	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * @param key
	 * @param field
	 * @param value
	 * @return 如果存在返回0 异常返回null
	 */
	public static Long hset(String key, String field, String value) {
		return execute(new RedisCallback<Long>() {
			public Long call(Jedis jedis, Object parms) {
				String key = ((Object[]) parms)[0].toString();
				String field = ((Object[]) parms)[1].toString();
				String value = ((Object[]) parms)[2].toString();
				return jedis.hset(key, field, value);
			}
		}, key, field, value);
	}

	/**
	 * Hash  批量添加 key value
	 * @param key
	 * @param values
	 */
	public static String hmset(String key,Map<String,String> values){
		return execute(new RedisCallback<String>() {
			@Override
			@SuppressWarnings("unchecked")
			public String call(Jedis jedis, Object param) {
				String key = ((Object[]) param)[0].toString();
				Map<String,String> hash = (Map<String,String>)(((Object[])param)[1]);
				return jedis.hmset(key,hash);
			}
		},key,values);
	}

	/**
	 * 批量添加 key value ，并设置过期时间
	 * @param key
	 * @param values
	 */
	public static String hmset(String key,Map<String,String> values,int second){
		return execute(new RedisCallback<String>() {
			@Override
			@SuppressWarnings("unchecked")
			public String call(Jedis jedis, Object param) {
				String key = ((Object[]) param)[0].toString();
				Map<String,Object> hash = (Map<String,Object>)(((Object[])param)[1]);
				Map<String,String> redisMap = new HashMap<String, String>();
				for (Map.Entry<String,Object> entry : hash.entrySet()){
					redisMap.put(entry.getKey(),entry.getValue() == null ? "" : entry.getValue().toString());
				}
				String second = ((Object[]) param)[2].toString();
				String result = jedis.hmset(key,redisMap);
				jedis.expire(key,Integer.parseInt(second));
				return result;
			}
		},key,values,second);
	}

	/**
	 * 将map 对象 转换成 json 字符串，保存
	 * @param key
	 * @param values
	 * @param second
     * @return
     */
	public static String setJsonStr(String key,Map<String,Object> values,int second){
		return execute(new RedisCallback<String>() {
			@Override
			public String call(Jedis jedis, Object param) {
				Object[] paramArray = (Object[])param;
				String redisKey = paramArray[0].toString();
				Map<String,Object> valueMap = (Map<String, Object>) paramArray[1];
				JSONObject jsonObject = new JSONObject();
				String redisValue = jsonObject.fromObject(valueMap).toString();
				Integer expireTime = Integer.parseInt(paramArray[2].toString());
				return set(redisKey,redisValue,expireTime);
			}
		},key,values,second);
	}

	/**
	 * 获取redis 值 并转换成map
	 * @param key
	 * @return
     */
	public static Map<String,Object> getJsonToMap(String key){
		return execute(new RedisCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> call(Jedis jedis, Object param) {
				Object[] paramArray = (Object[])param;
				String redisKey = paramArray[0].toString();
				String jsonStr = jedis.get(redisKey);
				JSONObject jsonObject = new JSONObject();
				Map<String,Object>  valueMap = (Map<String,Object>) jsonObject.fromObject(jsonStr);
				return valueMap;
			}
		},key);
	}


	//=======================================String=======================================//
	/**
	 * 字符串 获取
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return execute(new RedisCallback<String>() {
			public String call(Jedis jedis, Object parms) {
				String key = ((Object[]) parms)[0].toString();
				return jedis.get(key);
			}
		}, key);
	}

	/**
	 * 字符串 赋值
	 * @param key
	 * @param value
	 */
	public static String set(String key, String value) {
		return execute(new RedisCallback<String>() {
			public String call(Jedis jedis, Object parms) {
				String key = ((Object[]) parms)[0].toString();
				String value = ((Object[]) parms)[1].toString();
				return jedis.set(key, value);
			}
		},key, value);
	}

	/**
	 * 字符串 赋值 ，并设置过期时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static String set(String key, String value, int seconds) {
		return execute(new RedisCallback<String>() {
			public String call(Jedis jedis, Object parms) {
				String key = ((Object[]) parms)[0].toString();
				String value = ((Object[]) parms)[1].toString();
				String seconds = ((Object[]) parms)[2].toString();
				return jedis.setex(key, Integer.parseInt(seconds), value);
			}
		},key, value, seconds);
	}

	/**
	 * 字符串  批量添加
	 * @param valueMap
	 */
	public static void setPipeLine(Map<String,String> valueMap) {
		execute(new RedisCallback<String>() {
			@SuppressWarnings("unchecked")
			public String call(Jedis jedis, Object parms) {
				Pipeline p = jedis.pipelined();
				Map<String,String> values = (Map<String,String>) ((Object[]) parms)[0];
				for (Map.Entry<String,String> entry : values.entrySet()) {
					p.set(entry.getKey(),entry.getValue());
				}
				p.sync();
				return null;
			}
		}, valueMap);
	}

	//=======================================Set=======================================//
	/**
	 * set 集合 添加元素
	 * @param key
	 * @param value
	 */
	public static Long sadd(String key, String value) {
		return execute(new RedisCallback<Long>() {
			public Long call(Jedis jedis, Object parms) {
				String key = ((Object[]) parms)[0].toString();
				String value = ((Object[]) parms)[1].toString();
				return jedis.sadd(key, value);
			}
		},key, value);
	}

	/**
	 * 返回集合 key 中的所有成员。
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		return execute(new RedisCallback<Set<String>>() {
			public Set<String> call(Jedis jedis, Object parms) {
				Object[] ps = ((Object[]) parms);
				String key = ps[0].toString();
				return jedis.smembers(key);
			}
		},key);
	}

	public static void main(String[] args){
		Map<String,Object> hmsetMap = new HashMap<String,Object>();
		hmsetMap.put("a","1");
		hmsetMap.put("b",null);
		Map<String,Object> value = new HashMap<String,Object>();
		value.put("c","xu");
		hmsetMap.put("c",value);
        RedisUtil.setJsonStr("hmset1",hmsetMap,30);
		Map<String,Object> setAll = RedisUtil.getJsonToMap("hmset1");
		for (Map.Entry<String,Object> entry:setAll.entrySet()){
			System.out.println(entry.getKey() + "----"+entry.getValue());
		}
	}

	/**
	 * 将对象保存到redis中
	 * @param
	 * @return 
	 * @version <1> 2019/3/6 16:49 zhangshen:Created.
	 */
	public static String setJsonObj(String key,Object object,int second){
		return execute(new RedisCallback<String>() {
			@Override
			public String call(Jedis jedis, Object param) {
				Object[] paramArray = (Object[])param;
				String redisKey = paramArray[0].toString();
				Object obj = (Object) paramArray[1];
				Integer expireTime = Integer.parseInt(paramArray[2].toString());
				return set(redisKey,JSON.toJSONString(obj) ,expireTime);
			}
		},key,object,second);
	}

	/**
	 * 根据key获取对象
	 * @param
	 * @return 
	 * @version <1> 2019/3/6 16:50 zhangshen:Created.
	 */
	public static Object getJsonObj(String key,Class clazz){
		return execute(new RedisCallback<Object>() {
			@Override
			public Object call(Jedis jedis, Object param) {
				Object[] paramArray = (Object[])param;
				String redisKey = paramArray[0].toString();
				String jsonStr = jedis.get(redisKey);
				return JSON.parseObject(jsonStr,clazz);
			}
		},key);
	}

}
