package com.hope.xueling.common.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hope.xueling.common.domain.dto.UserDTO;
import com.hope.xueling.common.domain.entity.User;
import com.hope.xueling.common.domain.vo.UserVO;
import com.hope.xueling.common.exception.BusinessException;
import com.hope.xueling.common.mapper.UserMapper;
import com.hope.xueling.common.service.IUserService;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * @author 谢光益
 * @since 2026/1/20
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final String DEFAULT_AVATAR_URL = "https://xueling-platform.oss-cn-hangzhou.aliyuncs.com/d915734b-a240-4350-acb6-30b6e2d3d981.jpg";

    @Override
    public void insertUser(User user) {
        long userId = IdUtil.getSnowflakeNextId();
        user.setId(userId);
        //查询此时用户总数
        long count = userMapper.selectCount(null);
        long index = count + 1;
        user.setUsername("学灵"+index);
        user.setAvatarUrl(DEFAULT_AVATAR_URL);
        userMapper.insert(user);
    }

    @Override
    public User getUserByEmailWithPassword(String email) {
        //邮箱不能为空
        if (email == null) {
            throw new ValidationException("邮箱不能为空");
        }
        //正则表达式校验
        if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
            throw new ValidationException("邮箱格式错误");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户手机号查用户信息
     * @param phone 手机号
     * @return User 用户实体类
     */
    @Override
    public User getUserByPhoneWithPassword(String phone) {
        //手机号不能为空
        if (phone == null) {
            throw new ValidationException("手机号不能为空");
        }
        //正则表达式校验
        if (!phone.matches("^1[3456789]\\d{9}$")) {
            throw new ValidationException("手机号格式错误");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return userMapper.selectOne(queryWrapper);
    }


    @Override
    public void updateUserById(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userDTO.getId());
        userMapper.update(user, updateWrapper);
    }


    @Override
    public UserVO getUserInfo(Long id) {
        //检查id是否为空
        if (id == null) {
            throw new ValidationException("用户ID不能为空");
        }
        //根据用户ID查询用户信息
        User user = userMapper.selectById(id);
        if (user != null) {
            //将用户实体类转换为用户数据传输对象
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }
        //用户不存在异常
        throw new BusinessException("用户不存在");
    }

    /**
     * 根据邮箱和手机号获取密码
     * @param email 邮箱
     * @return String 密码
     */
    @Override
    public String getPasswordByEmail(String email) {
        //检查邮箱是否为空
        if (email == null) {
            //邮箱不能为空异常
            throw new ValidationException("邮箱不能为空");
        }
        //根据邮箱查询用户密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        String password = userMapper.selectOne(queryWrapper).getPassword();
        //如果密码为空，则抛出异常
        if (password == null) {
            //邮箱或手机号错误异常
            throw new BusinessException("不存在该邮箱");
        }
        return password;
    }

    /**
     * 根据手机号获取密码
     * @param phone 手机号
     * @return String 密码
     */
    @Override
    public String getPasswordByPhone(String phone) {
        //检查手机号是否为空
        if (phone == null) {
            //手机号不能为空异常
            throw new ValidationException("手机号不能为空");
        }
        //根据手机号查询用户密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        String password = userMapper.selectOne(queryWrapper).getPassword();
        //如果密码为空，则抛出异常
        if (password == null) {
            //邮箱或手机号错误异常
            throw new BusinessException("不存在该手机号");
        }
        return password;
    }

    @Override
    public void cancelUserAccount(Long userId, String verificationCode) {
        //检查用户ID是否为空
        if (userId == null) {
            //用户ID不能为空异常
            throw new ValidationException("用户ID不能为空");
        }

        //TODO 验证码校验

        //TODO 注销用户账号
    }
}
