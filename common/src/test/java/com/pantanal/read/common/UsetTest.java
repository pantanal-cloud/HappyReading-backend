package com.pantanal.read.common;

import com.pantanal.read.common.dao.RedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsetTest {

    @Resource
    private RedisDao redisDao;

    @Test
    public void testQuery() {
        redisDao.exec(jedis -> {
            return jedis.zadd("TEST", 10, "gudong7");
        });
        redisDao.exec(jedis -> {
            return jedis.zadd("TEST", 10, "gudong8");
        });
        Long reuslt = redisDao.exec(jedis -> {
            return jedis.zcard("TEST");
        });
        System.out.println( reuslt.toString());
    }

}
