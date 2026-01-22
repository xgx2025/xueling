package com.hope.xueling.common.service.impl;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.dto.RegisterDTO;
import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.exception.BusinessException;
import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.common.service.IAuthService;
import com.hope.xueling.common.util.EmailVerificationCodeUtils;
import com.hope.xueling.common.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类，处理用户登录、注册等操作
 * @author 谢光湘
 * @since 2026/1/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    /**
     * 用户服务实现类
     */
    private final UserServiceImpl userService;
    /**
     * 密码加密器
     */
    private final BCryptPasswordEncoder passwordEncoder;
    /**
     * 邮箱验证码工具类
     */
    private final EmailVerificationCodeUtils emailVerificationCodeUtils;
    /**
     * Redis模板，用于操作Redis缓存
     */
    private final StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_KEY_PREFIX = "refreshToken:";


    @Override
    public void register(RegisterDTO registerDTO) {
        // 检验参数
        String email = registerDTO.getEmail();
        String phone = registerDTO.getPhone();
        String rawPassword = registerDTO.getPassword();
        String confirmPassword = registerDTO.getConfirmPassword();
        String verificationCode = registerDTO.getVerificationCode();

        boolean isEmailRegister = StringUtils.hasText(email);
        boolean isPhoneRegister = StringUtils.hasText(phone);
        if(!isEmailRegister && !isPhoneRegister){
            throw new ValidationException("邮箱或手机号不能为空");
        }
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        if(!StringUtils.hasText(rawPassword) || !rawPassword.matches(passwordRegex)){
            throw new ValidationException("密码格式错误，密码至少8位，包含大小写字母和数字");
        }
        if(!rawPassword.equals(confirmPassword)){
            throw new ValidationException("两次输入的密码不一致");
        }
        //验证验证码
        if(!StringUtils.hasText(verificationCode)){
            throw new ValidationException("验证码不能为空");
        }
        // 验证验证码
        if(isEmailRegister){
            emailVerificationCodeUtils.verifyCode(email, verificationCode);
        }else{
            // TODO: 验证手机号验证码
            throw new BusinessException("手机号注册暂未支持");
        }
        // 密码加密
        String encodedPassword = encodePassword(rawPassword);
        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(encodedPassword);
        // 保存用户
        userService.insertUser(user);
    }

    @Override
    public Map<String,String> login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String phone = loginDTO.getPhone();
        String rawPassword = loginDTO.getPassword();
        User user = null;

        if(!StringUtils.hasText(rawPassword)){
            throw new ValidationException("密码不能为空");
        }

        boolean isEmailLogin = StringUtils.hasText(email);
        boolean isPhoneLogin = StringUtils.hasText(phone);

        if (isEmailLogin){
            user = userService.getUserByEmailWithPassword(email);
        }else if (isPhoneLogin) {
            user = userService.getUserByPhoneWithPassword(phone);
        }else {
            log.warn("登录失败，未提供邮箱或手机号");
            throw new BusinessException("无效的登录方式,请提供邮箱或手机号");
        }

        if (user == null || !checkPassword(rawPassword, user.getPassword())) {
            String account = isEmailLogin ? email : phone;
            log.warn("账号: {} 登录失败，密码错误", account);
            throw new BusinessException("账号或密码错误");
        }

        // 生成双token
        return generateTokens(user);
    }

    @Override
    public void logout(String userId) {
        // 删除Redis中的刷新令牌
        stringRedisTemplate.delete(REDIS_KEY_PREFIX+userId);

    }

    @Override
    public void sendEmailVerificationCode(String email,String userIP) {
        emailVerificationCodeUtils.generateAndSendCode(email,userIP);
    }

    @Override
    public void sendPhoneVerificationCode(String phone,String userIP) {
        // TODO: 发送手机号验证码
        throw new BusinessException("手机号登录暂未支持");
    }

    /**
     * 生成双token（访问令牌和刷新令牌）
     * @param user 用户实体对象
     * @return 包含访问令牌和刷新令牌的Map对象
     */
    public Map<String, String> generateTokens(User user) {
        String userId = user.getId().toString();
        //移除旧的刷新令牌
        stringRedisTemplate.delete(REDIS_KEY_PREFIX+userId);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        String accessToken = JwtTokenUtils.generateAccessToken(claims);
        String refreshToken = JwtTokenUtils.generateRefreshToken(claims);
        // 刷新令牌存储到Redis中，键为REDIS_KEY_PREFIX + userId，值为refreshToken，过期时间为7天
        stringRedisTemplate.opsForValue().set(REDIS_KEY_PREFIX+userId, refreshToken, JwtTokenUtils.REFRESH_TOKEN_EXPIRE, TimeUnit.MILLISECONDS);
        return Map.of("accessToken",accessToken,"refreshToken",refreshToken);
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
