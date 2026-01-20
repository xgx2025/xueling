package com.hope.xueling.common.service.impl;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 认证服务实现类，处理用户登录、注册等操作
 * @author 谢光湘
 * @date： 2026/1/20
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    /**
     * 用户登录
     * @param loginDTO 登录DTO对象，包含邮箱、手机号和密码
     * @return 登录成功的用户对象，登录失败返回null
     */
    @Override
    public User login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String phone = loginDTO.getPhone();
        String password = loginDTO.getPassword();
        if (email != null) {
            // 验证邮箱格式
            if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
                log.warn("邮箱格式无效");
                throw new ValidationException("邮箱格式无效");
            }
            log.info("正在使用邮箱: {} 登录", email);
        } else if (phone != null) {
            // 验证手机号格式
            if (!phone.matches("^1[3456789]\\d{9}$")) {
                log.warn("手机号格式无效");
               throw new ValidationException("手机号格式无效");
            }
            log.info("正在使用手机号: {} 登录", phone);
        } else {
            // 无效登录方式
            log.warn("无效的登录方式");
            throw new ValidationException("无效的登录方式");
        }
        return null;
    }

    /**
     * 密码加密
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    /**
     * 密码校验
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码校验结果，true表示密码匹配，false表示密码不匹配
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
