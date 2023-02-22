package com.atyjh.demo.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atyjh.demo.entity.LoginUser;
import com.atyjh.demo.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.atyjh.demo.common.CommonConstants.LOGIN_KEY;

/**
 * Copyright: Copyright(c) 2022 iwhalecloud
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: JwtAuthenticationTokenFilter.java
 *
 * @author huang.yijie
 * 时间: 2023/2/21 20:49
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取Header中的token
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            // 直接放行，让SpringSecurity的其他过滤器报错误
            filterChain.doFilter(request, response);
            return;
        }

        // 解析Token
        Long userId = null;
        try {
            userId = JwtHelper.getUserId(token);
        } catch (Exception e) {
            throw new RuntimeException("Token不合法!");
        }

        // 获取Redis中的用户信息
        String value = redisTemplate.opsForValue().get(LOGIN_KEY + userId);
        LoginUser loginUser = BeanUtil.toBean(value, LoginUser.class);

        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("登陆已失效！请重新登录！");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);

        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}
