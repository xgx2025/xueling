package com.hope.xueling.common.service;

import com.hope.xueling.common.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口
 */
@Service
public interface UserService {
    /**
     * 根据用户邮箱查用户信息
     */
    User getUserByEmail(String email);

    /**
     * 根据用户手机号查用户信息
     * @param phone 手机号
     * @return User 用户实体类
     */
    User getUserByPhone(String phone);
}
