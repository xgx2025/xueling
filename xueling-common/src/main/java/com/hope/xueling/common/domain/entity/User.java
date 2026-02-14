package com.hope.xueling.common.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 * @author 谢光益
 * @since 2026/1/21
 */
@Data
@NoArgsConstructor
public class User {
    //用户ID
    private Long id;
    //用户名
    private String username;
    //邮箱
    private String email;
    //手机号
    private String phone;
    //密码
    private String password;
    //头像URL
    private String avatarUrl;
    //性别
    private Integer gender;
    //生日
    private String birthday;
    //个人简介
    private String bio;
    //vip等级
    private Integer vipLevel;
    //vip开始时间
    private String vipStartAt;
    //vip到期时间
    private String vipExpireAt;
}
