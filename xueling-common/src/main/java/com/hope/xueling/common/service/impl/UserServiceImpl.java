package com.hope.xueling.common.service.impl;

import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.mapper.UserMapper;
import com.hope.xueling.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    //注入依赖

     private final UserMapper userMapper;//用户Mapper

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据用户邮箱查用户信息
     */
    @Override
    public User getUserByEmail(String email) {
        //如果邮箱不为空，则返回邮箱对应的用户信息，否则返回null
        if (email != null) {
            return userMapper.selectByEmail(email);
        }
        return null;
    }

    /**
     * 根据用户手机号查用户信息
     * @param phone 手机号
     * @return User 用户实体类
     */
    @Override
    public User getUserByPhone(String phone) {
        //如果手机号不为空，则返回手机号对应的用户信息，否则返回null
        if (phone != null) {
            return userMapper.selectByPhone(phone);
        }
        return null;
    }
 }
