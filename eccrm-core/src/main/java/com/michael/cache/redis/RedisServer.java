package com.michael.cache.redis;

import redis.clients.jedis.ShardedJedis;

/**
 * Redis相关的服务接口
 *
 * @author Michael
 */
public interface RedisServer {

    /**
     * 获得一个Redis客户端（注意，在使用完毕后需要手动close掉）
     *
     * @return redis客户端对象
     */
    ShardedJedis getRedisClient();

    /**
     * 执行一个Redis命令
     *
     * @param command redis命令对象
     */
    <T> T execute(RedisCommand<T> command);
}
