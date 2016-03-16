package com.michael.cache.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author Michael
 */
public interface RedisCommand<T> {
    T invoke(ShardedJedis shardedJedis, ShardedJedisPool pool);
}
