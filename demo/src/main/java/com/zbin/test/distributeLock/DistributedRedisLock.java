package com.zbin.test.distributeLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * Created by xxxxxz on 2019/2/1.
 */
public class DistributedRedisLock {
    private final JedisPool jedisPool;
    public  static int n = 500;
    public DistributedRedisLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 加锁
     * @param locaName  锁的key
     * @param acquireTimeout  获取超时时间
     * @param timeout   锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String locaName,
                                  long acquireTimeout, long timeout) {
        Jedis conn = null;
        String retIdentifier = null;
        try {
            // 获取连接
            conn = jedisPool.getResource();
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int)(timeout / 1000);

            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 释放锁
     * @param lockName 锁的key
     * @param identifier    释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始事务
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }

    public static void runDemo(DistributedRedisLock lock){
        String indentifier = lock.lockWithTimeout("resource", 5000, 1000);
        if(indentifier != null){
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            System.out.println(--lock.n+" "+indentifier);

            lock.releaseLock("resource", indentifier);
        }else{
            System.out.println("获取锁失败");
        }


    }
    public static void main(String[] args) {

        DistributedRedisLock lock = new DistributedRedisLock(getJedisPool());
        for (int i = 0; i < 50; i++) {
            new Thread(() ->DistributedRedisLock.runDemo(lock)).start();
        }
    }
    public static JedisPool getJedisPool(){
        JedisPool pool = null;


            JedisPoolConfig config = new JedisPoolConfig();
            // 设置最大连接数
            config.setMaxTotal(200);
            // 设置最大空闲数
            config.setMaxIdle(8);
            // 设置最大等待时间
            config.setMaxWaitMillis(1000 * 100);
            // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, "47.106.214.38", 6379, 3000);
        return pool;
    }
}
