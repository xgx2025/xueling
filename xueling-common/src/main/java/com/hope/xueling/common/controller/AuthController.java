package com.hope.xueling.common.controller;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.dto.RegisterDTO;
import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证控制器，处理登录、登出、注册和忘记密码等请求
 * @author 谢光湘
 * @since 2026/1/20
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @RequestMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return Result.success("注册成功");
    }

    /**
     * 登录
     * @param loginDTO 登录数据传输对象
     * @param request HttpServletRequest对象
     * @return 登录成功后的用户信息
     */
    @RequestMapping("/login")
    public Result<Map<String,String>> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        log.info("IP: {} 正在尝试登录, Email: {}, Phone: {}", request.getRemoteAddr(), loginDTO.getEmail(), loginDTO.getPhone());
        loginDTO.setIp(request.getRemoteAddr());
        Map<String,String> tokens = authService.login(loginDTO);
        return Result.success(tokens);
    }
    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping("/forget")
    public String forget() {
        return "forget";
    }
}
