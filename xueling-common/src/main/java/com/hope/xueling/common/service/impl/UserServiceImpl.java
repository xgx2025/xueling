package com.hope.xueling.common.service.impl;

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
@RequiredArgsConstructor  // Lombok自动生成构造器
public class UserServiceImpl implements IUserService {

    //注入依赖
    private final UserMapper userMapper;//用户Mapper


    /**
     * 根据用户邮箱查用户信息
     * @param email 邮箱
     * @return User 用户实体类
     */
    @Override
    public User getUserByEmail(String email) {
        //邮箱不能为空
        if (email == null) {
            throw new ValidationException("邮箱不能为空");
        }
        //正则表达式校验
        if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
            throw new ValidationException("邮箱格式错误");
        }
        return userMapper.selectByEmail(email);
    }

    /**
     * 根据用户手机号查用户信息
     * @param phone 手机号
     * @return User 用户实体类
     */
    @Override
    public User getUserByPhone(String phone) {
        //手机号不能为空
        if (phone == null) {
            throw new ValidationException("手机号不能为空");
        }
        //正则表达式校验
        if (!phone.matches("^1[3456789]\\d{9}$")) {
            throw new ValidationException("手机号格式错误");
        }
        return userMapper.selectByPhone(phone);
    }

    /**
     * 更新用户信息
     * @param userDTO 用户数据传输对象
     */
    @Override
    public void updateUser(UserDTO userDTO) {
        //根据用户ID更新用户信息
        userMapper.updateById(userDTO);
    }

    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return UserDTO 用户数据传输对象
     */
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
        String password = userMapper.selectPasswordByEmail(email);
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
        String password = userMapper.selectPasswordByPhone(phone);
        //如果密码为空，则抛出异常
        if (password == null) {
            //邮箱或手机号错误异常
            throw new BusinessException("不存在该手机号");
        }
        return password;
    }
}
