package com.zbin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zbin.constants.RedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class RedisUtil {
    final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    /**
     *
     * @param redisTemplate redis数据源
     * @param key           Hash格式的子key
     * @param cls
     * @param <T>
     * @return  返回单个对象
     * @throws Exception
     */
    public static <T> T readRedisObject(RedisTemplate<String, String> redisTemplate, RedisKey key, Class<T> cls) {
        String value = (String) redisTemplate.opsForHash().get(key.getFather(), key.getName());
        if(StringUtils.isEmpty(value)) {
            logger.info("redis查询为空, RedisKey = " + key + " value = " + value);
            return null;
        }
        JSONObject json = JSONObject.parseObject(value);
        String valueStr = json == null ? null : json.getString("Value");
        if(!StringUtils.isEmpty(valueStr)){
            logger.info("redis查询成功, RedisKey = " + key + " valueStr = " + (valueStr.length() <= 50 ? valueStr : valueStr.substring(0, 50)) + "...");
        }else{
            logger.info("redis查询成功, RedisKey = " + key + " valueStr = [empty]");
        }
        return StringUtils.isEmpty(valueStr) ? null : JSONObject.parseObject(valueStr, cls);
    }


    /**
     *
     * @param redisTemplate redis数据源
     * @param key Hash格式的子key
     * @param clazz
     * @return 返回List对象、如果设置了过期时间、则判断是否过期<br />过期或取不到数据返回null
     */
    public static <T> List<T> readRedisList(RedisTemplate<String, String> redisTemplate, RedisKey key, Class<T> clazz) {
        String jsonStr = (String) redisTemplate.opsForHash().get(key.getFather(), key.getName());
        if(StringUtils.isEmpty(jsonStr)) {
            logger.info("redis查询为空, RedisKey = " + key + " jsonStr = " + jsonStr);
            return null;
        }
        JSONObject json = JSON.parseObject(jsonStr);
        if(json == null) {
            logger.info("redis转换失败, RedisKey = " + key + " JSONObject = " + json);
            return null;
        }
        String value = json.getString("Value");

        return StringUtils.isEmpty(value) ? null : JSONObject.parseArray(value, clazz);
    }
    /**
     *一次查询多个
     * @param redisTemplate redis数据源
     * @param key  父key
     * @param childKeys 子key集合
     * @param cls 返回的类型
     * @return  List<T>
     * @throws Exception
     */
    public static <T> List<T> readRedisList(RedisTemplate<String, String> redisTemplate, RedisKey key, List<RedisKey> childKeys, Class<T> cls) {
        List<Object> keyNames = new ArrayList<>();
        for (RedisKey redisKey : childKeys) {
            keyNames.add(redisKey.getName());
        }
        List<T> list = Lists.newArrayList();
        for (Object obj : redisTemplate.opsForHash().multiGet(key.getName(), keyNames)) {
            if(obj == null) {
                continue;
            }
            JSONObject json = JSONObject.parseObject(obj.toString());
            String valueObj = json == null ? null : json.getString("Value");
            if (StringUtils.isEmpty(valueObj)) {
                continue;
            }
            T t = JSONObject.parseObject(valueObj, cls);
            if(t != null) {
                list.add(t);
            }
        }
        logger.info("redis查询成功, RedisKey = " + key + " childKeys = " + childKeys + " listSize = " + list.size());
        return list;
    }



    /**
     * hash通用写redis
     *
     * @param redisTemplate
     * @param key
     * @param data
     */
    public static void writeRedisObject(RedisTemplate<String,String> redisTemplate, RedisKey key, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Key", key.getName());
        jsonObject.put("Value", data);
        String jsonStr = jsonObject.toJSONString();

        redisTemplate.opsForHash().put(key.getFather(), key.getName(), jsonStr);
        if(key.getTimeout() != null && key.getTimeUnit() != null){
            redisTemplate.expire(key.getFather(), key.getTimeout(), key.getTimeUnit());
        }
    }


    /**
     *
     * @param redisTemplate redis数据源
     * @param key Hash格式的子key
     * @param clazz
     * @return
     */
    public static <T> List<T> readList(RedisTemplate<String, String> redisTemplate, RedisKey key, Class<T> clazz) {
        return read(redisTemplate, key.getFather(), key.getName(), clazz);
    }


    private static <T> List<T> read(RedisTemplate<String, String> redisTemplate, String key, String hashKey, Class<T> clazz) {
        String jsonStr = (String) redisTemplate.opsForHash().get(key, hashKey);
        if(StringUtils.isEmpty(jsonStr)) {
            logger.debug("redis查询为空, Key = " + key + " HashKey = "+ hashKey + " jsonStr = " + jsonStr);
            return null;
        }
        try {
            return JSONObject.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("redis查询为空, Key = " + key + " HashKey = "+ hashKey + " jsonStr = " + jsonStr, e);
        }
        return null;
    }
}
