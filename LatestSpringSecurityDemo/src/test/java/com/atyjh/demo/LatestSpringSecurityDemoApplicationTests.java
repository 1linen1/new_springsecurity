package com.atyjh.demo;

import com.atyjh.demo.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class LatestSpringSecurityDemoApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name", "Lily");
    }

    @Test
    void test() {
        String password1 = passwordEncoder.encode("123456");
        String password2 = passwordEncoder.encode("123456");
        System.out.println(password1);
        System.out.println(password2);

        System.out.println(passwordEncoder.matches("123456", password1));
    }

    @Test
    void test2() {
//        Long userId = JwtHelper.getUserId("123456.123.46465");
    }

}











