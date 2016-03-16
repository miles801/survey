package com.michael.cache.redis;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * @author Michael
 */
@Service
public class RedisServerImpl implements RedisServer {


    public RedisServerImpl() {
        Logger logger = Logger.getLogger(RedisServerImpl.class);
        logger.info("启动Redis服务...");
    }

    @Resource
    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        return shardedJedisPool.getResource();
    }

    @Override
    public <T> T execute(RedisCommand<T> command) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        T o = command.invoke(jedis, shardedJedisPool);
        if (!shardedJedisPool.isClosed()) {
            jedis.close();
        }
        return o;
    }

}
