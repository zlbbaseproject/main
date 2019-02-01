package com.zbin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis config
 */
@Configuration
public class RedisConfig {



    /*--------------------------------  /redis/web  -----------------------------------*/
    @Value("${spring.redis.host}")
    private String host = null;

    @Value("${spring.redis.port}")
    private String port = null;

    @Value("${spring.redis.password}")
    private String password = null;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdl;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdl;
    @Value("${spring.redis.database}")
    private int database;

    @Bean("redisPool")
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig redisPool() {
        return new JedisPoolConfig();
    }

    @Primary
    @Bean("redisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        return getRedisTemplate(jedisConnectionFactory());
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdl);
        poolConfig.setMinIdle(minIdl);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisPool());
        jedisConnectionFactory.setHostName(host);
        if(!password.isEmpty()){
            jedisConnectionFactory.setPassword(password);
        }
        jedisConnectionFactory.setPort(Integer.parseInt(port));
        jedisConnectionFactory.setDatabase(database);
        return jedisConnectionFactory;
    }



    private RedisTemplate<String, String> getRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        return redisTemplate;
    }

}
