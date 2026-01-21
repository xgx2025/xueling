package com.hope.xueling.common.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册DTO对象
 * @author 谢光湘
 * @since 2026/1/20
 */
@Data
@NoArgsConstructor
public class RegisterDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String confirmPassword;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String verificationCode;
}
