package com.hope.xueling.common.service.impl;

import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.exception.BaseException;
import com.hope.xueling.common.mapper.UserMapper;
import com.hope.xueling.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor  // Lombok自动生成构造器
public class UserServiceImpl implements UserService {

    //注入依赖
    private final UserMapper userMapper;//用户Mapper


    /**
     * 根据用户邮箱查用户信息
     * @param email 邮箱
     * @return User 用户实体类
     */
    @Override
    public User getUserByEmail(String email) {
        //如果邮箱不为空，则返回邮箱对应的用户信息，否则返回null
        if (email != null) {
            //正则表达式校验
            if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
                //邮箱格式错误异常
                throw new BaseException("邮箱格式错误");
            }
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
            //正则表达式校验
            if (!phone.matches("^1[3456789]\\d{9}$")) {
                //手机号格式错误异常
                throw new BaseException("手机号格式错误");
            }
            return userMapper.selectByPhone(phone);
        }
        return null;
    }
}
