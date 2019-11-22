package com.pantanal.read.common.dao;

import com.pantanal.read.common.func.JedisExecFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class RedisDao {
    @Autowired
    private JedisPool jedisPool;

    public <T> T exec(JedisExecFunc<T> exec) {
        Jedis jedis = jedisPool.getResource();
        T t = exec.callBack(jedis);
        jedis.close();
        return t;
    }
}
