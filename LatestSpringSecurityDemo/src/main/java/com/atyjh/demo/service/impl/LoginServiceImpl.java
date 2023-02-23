package com.atyjh.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.atyjh.demo.entity.LoginUser;
import com.atyjh.demo.entity.User;
import com.atyjh.demo.service.ILoginService;
import com.atyjh.demo.utils.JwtHelper;
import com.atyjh.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.atyjh.demo.common.CommonConstants.LOGIN_KEY;

/**
 * Copyright: Copyright(c) 2022 iwhalecloud
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: LoginServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/2/21 00:01
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result login(User user) {

        HashMap<String, Object> map = new HashMap<>();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // 使用ProviderManager 的auth方法进行验证
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误！");
        }

        // 我们直接实现的 UserDetailServiceImpl 类的返回值 LoginUser类 就是在存储在这里
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        User realUser = loginUser.getUser();

        // 生成token
        String token = JwtHelper.createToken(realUser.getId(), realUser.getUsername());

        // 存入Redis中
        redisTemplate.opsForValue().set(LOGIN_KEY + realUser.getId(),
                JSON.toJSONString(loginUser), 30 * 60 * 1000, TimeUnit.SECONDS);

        // 组装后返回给前端
        map.put("token", token);

        return Result.success(map, "登录成功！");
    }

    @Override
    public Result logout() {
        // 从上下文中获取用户信息
        SecurityContext context = SecurityContextHolder.getContext();
        LoginUser loginUser = (LoginUser) context.getAuthentication().getPrincipal();
        redisTemplate.delete(LOGIN_KEY + loginUser.getUser().getId());
        return Result.success("退出成功！");
    }
}















