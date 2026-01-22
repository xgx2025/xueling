package com.hope.xueling.common.controller;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.dto.RegisterDTO;
import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证控制器，处理登录、登出、注册和忘记密码等请求
 * @author 谢光湘
 * @since 2026/1/20
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {

    private final IAuthService authService;

    /**
     * 用户注册
     * @param registerDTO 注册数据传输对象
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return Result.success(null);
    }

    /**
     * 登录
     * @param loginDTO 登录数据传输对象
     * @param request HttpServletRequest对象
     * @return 登录成功后返回访问令牌和刷新令牌
     */
    @PostMapping("/login")
    public Result<Map<String,String>> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        log.info("IP: {} 正在尝试登录, Email: {}, Phone: {}", request.getRemoteAddr(), loginDTO.getEmail(), loginDTO.getPhone());
        loginDTO.setIp(request.getRemoteAddr());
        Map<String,String> tokens = authService.login(loginDTO);
        return Result.success(tokens);
    }

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return 发送成功的消息
     */
    @GetMapping("/sendVerificationCode/email")
    public Result<String> sendVerificationCode(@RequestParam String email, HttpServletRequest request) {
        log.info("IP: {} 正在尝试发送邮箱验证码, Email: {}", request.getRemoteAddr(), email);
        authService.sendEmailVerificationCode(email, request.getRemoteAddr());
        return Result.success(null);
    }


    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/forget")
    public String forget() {
        return "forget";
    }
}
