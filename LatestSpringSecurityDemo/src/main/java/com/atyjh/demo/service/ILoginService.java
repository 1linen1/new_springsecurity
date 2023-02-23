package com.atyjh.demo.service;

import com.atyjh.demo.entity.User;
import com.atyjh.demo.utils.Result;

public interface ILoginService {
    /**
     * 登录校验
     *
     * @param user
     * @return
     */
    public Result login(User user);

    /**
     * 退出登录
     *
     * @return
     */
    Result logout();
}
