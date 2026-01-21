package com.hope.xueling.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hope.xueling.common.domain.dto.UserDTO;
import com.hope.xueling.common.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return User 用户实体类
     */
    @Select("select * from user where email = #{email}")
    User selectByEmail(String email);

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return User 用户实体类
     */
    @Select("select * from user where phone = #{phone}")
    User selectByPhone(String phone);

    /**
     * 根据用户ID更新用户信息
     * @param userDTO 用户数据传输对象
     */
    void updateById(UserDTO userDTO);
}
