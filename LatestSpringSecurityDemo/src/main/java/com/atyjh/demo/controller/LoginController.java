package com.atyjh.demo.controller;

import com.atyjh.demo.entity.User;
import com.atyjh.demo.service.ILoginService;
import com.atyjh.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright: Copyright(c) 2022 iwhalecloud
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: LoginController.java
 *
 * @author huang.yijie
 * 时间: 2023/2/20 23:57
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return loginService.login(user);
    }

    @GetMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }

}
