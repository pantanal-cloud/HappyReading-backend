package com.pantanal.read.common.func;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisExecFunc<T> {
    T callBack(Jedis jedis);
}
