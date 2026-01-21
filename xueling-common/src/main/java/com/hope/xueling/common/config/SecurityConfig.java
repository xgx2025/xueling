package com.hope.xueling.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security配置类，定义密码加密器
 * @author 谢光湘
 * @date： 2026/1/20
 */
@Configuration
public class SecurityConfig {
    /**
     * 创建密码加密器
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
