package com.hope.xueling.common.service;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.dto.RegisterDTO;
import com.hope.xueling.common.domain.entity.User;

/**
 * 认证服务接口，定义了登录、登出、注册和忘记密码等操作
 * @author 谢光湘
 * @date 2026/1/20
 */
public interface IAuthService {
    /**
     * 用户注册
     * @param registerDTO 注册DTO对象，包含用户名、密码、确认密码、邮箱、手机号
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户登录
     * @param loginDTO 登录DTO对象，包含用户名和密码（支持邮箱、手机号登录）
     * @return 登录成功后的用户实体对象
     */
    User login(LoginDTO loginDTO);

     /**
      * 用户登出
      */
    void logout();

}
