package com.hope.xueling.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hope.xueling.common.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户ID更新用户账户状态为已注销(3-注销)
     * @param userId 用户ID
     */
    @Update("update user set account_status = 3 where id = #{userId}")
    void updateAccountStatusById(Long userId);
}
