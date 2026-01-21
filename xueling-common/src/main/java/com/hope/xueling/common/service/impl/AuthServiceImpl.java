package com.hope.xueling.common.service.impl;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.dto.RegisterDTO;
import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.exception.BusinessException;
import com.hope.xueling.common.service.IAuthService;
import com.hope.xueling.common.util.EmailVerificationCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     * 用户注册(支持邮箱或手机号注册)
     * @param registerDTO 注册DTO对象，包含用户名、密码、确认密码、邮箱、手机号
     */
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
        emailVerificationCodeUtils.verifyCode(email, verificationCode);
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
    public User login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String phone = loginDTO.getPhone();
        String rawPassword = loginDTO.getPassword();
        String dbPassword;

        if(!StringUtils.hasText(rawPassword)){
            throw new ValidationException("密码不能为空");
        }

        boolean isEmailLogin = StringUtils.hasText(email);
        boolean isPhoneLogin = StringUtils.hasText(phone);

        if (isEmailLogin){
            dbPassword = userService.getPasswordByEmail(email);
        }else if (isPhoneLogin) {
            dbPassword = userService.getPasswordByPhone(phone);
        }else {
            log.warn("登录失败，未提供邮箱或手机号");
            throw new ValidationException("无效的登录方式,请提供邮箱或手机号");
        }

        if (dbPassword == null || !checkPassword(rawPassword, dbPassword)) {
            String account = isEmailLogin ? email : phone;
            log.warn("账号: {} 登录失败，密码错误", account);
            throw new BusinessException("账号或密码错误");
        }

        if (isEmailLogin){
            return userService.getUserByEmail(email);
        }else {
            return userService.getUserByPhone(phone);
        }
    }

    @Override
    public void logout() {

    }

    @Override
    public void sendEmailVerificationCode(String email,String userIP) {
        emailVerificationCodeUtils.generateAndSendCode(email,userIP);
    }

    @Override
    public void sendPhoneVerificationCode(String phone,String userIP) {

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
