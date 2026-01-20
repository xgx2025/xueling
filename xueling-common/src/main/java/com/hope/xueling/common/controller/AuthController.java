package com.hope.xueling.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器，处理登录、登出、注册和忘记密码等请求
 * @author 谢光湘
 * @date 2026/1/20
 */
@Slf4j
@RestController
@RequestMapping
public class AuthController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping("/forget")
    public String forget() {
        return "forget";
    }
}
