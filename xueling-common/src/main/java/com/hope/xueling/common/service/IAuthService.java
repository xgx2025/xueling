package com.hope.xueling.common.service;

import com.hope.xueling.common.domain.dto.LoginDTO;
import com.hope.xueling.common.domain.entity.User;

/**
 * 认证服务接口，定义了登录、登出、注册和忘记密码等操作
 * @author 谢光湘
 * @date 2026/1/20
 */
public interface IAuthService {
    User login(LoginDTO loginDTO);


}
