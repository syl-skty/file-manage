package com.skty.plugins.filemanage;

import com.skty.plugins.filemanage.service.DirectoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author zhaoyun
 * @date 2020/9/29 11:18
 */
public class RedisTest extends BaseTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DirectoryService service;

    @Test
    public void testReadRedis() {
        Long name = stringRedisTemplate.boundValueOps("name").getExpire();
        System.out.println(name);
        service.getChildElements(0L);
    }
}
