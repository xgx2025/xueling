package com.hope.xueling.common.controller;


import com.hope.xueling.common.domain.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    //TODO 用户登录、注册、忘记密码、修改密码、修改用户信息、获取用户信息、用户注销

    //TODO 编辑用户信息
    /**
     * 编辑用户信息
     */
    @PostMapping("/profile")
    public Result<String> profile() {
        return Result.success("");
    }


}
