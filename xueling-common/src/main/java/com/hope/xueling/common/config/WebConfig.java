package com.hope.xueling.common.config;

import com.hope.xueling.common.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，注册拦截器
 * @author 谢光湘
 * @since 2026/1/23
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns
                ("/druid/**","/auth/login","/auth/register","/auth/refreshToken", "/templates/error", "/auth/sendVerificationCode/**", "/auth/forget", "/admin/**");
    }
}
