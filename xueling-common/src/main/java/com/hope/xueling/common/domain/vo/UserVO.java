package com.hope.xueling.common.domain.vo;

import lombok.Data;

/**
 * 用户视图对象
 * @author 谢光益
 * @date 2026/1/21
 */
@Data
public class UserVO {
    //用户ID
    private Long id;
    //用户名
    private String username;
    //邮箱
    private String email;
    //手机号
    private String phone;
    //昵称
    private String nickname;
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
    private String vipStartTime;
    //vip到期时间
    private String vipExpireTime;
}
