package com.hope.xueling.common.interceptor;

import com.hope.xueling.common.util.JwtTokenUtils;
import com.hope.xueling.common.util.ThreadLocalUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
/**
 * 登录拦截器，拦截需要认证的请求，验证JWT令牌
 * @author 谢光湘
 * @since 2026/1/22
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 预处理回调方法，在控制器处理请求之前调用
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param handler 处理请求的处理器对象
     * @return 如果返回true，则继续处理请求；如果返回false，则中断请求处理流程
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        log.info("IP: {} 正在尝试访问 {}", request.getRemoteAddr(), request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        try{
            String token = authorization.substring(7).trim();
            Claims claims = JwtTokenUtils.getClaimsFromToken(token, JwtTokenUtils.ACCESS_TOKEN_SECRET);
            ThreadLocalUtils.set(claims);
            return true;
        }catch (Exception e){
            log.error("JWT令牌解析失败---未登录", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

}
