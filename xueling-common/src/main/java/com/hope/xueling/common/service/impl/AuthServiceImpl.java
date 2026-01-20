package com.hope.xueling.common.service.impl;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    @Override
    public User login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String phone = loginDTO.getPhone();
        String password = loginDTO.getPassword();
        if (email != null) {
            // 验证邮箱格式
            if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
                log.warn("邮箱格式无效");
                return null;
            }
            log.info("正在使用邮箱: {} 登录", email);
        } else if (phone != null) {
            // 验证手机号格式
            if (!phone.matches("^1[3456789]\\d{9}$")) {
                log.warn("手机号格式无效");
                return null;
            }
            log.info("正在使用手机号: {} 登录", phone);
        } else {
            // 无效登录方式
            log.warn("无效的登录方式");
        }
        return null;
    }
}
