CREATE TABLE users (
    -- 基础信息
                       id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
                       uuid CHAR(36) UNIQUE NOT NULL COMMENT '全局唯一标识符',

    -- 账户信息
                       username VARCHAR(50) UNIQUE COMMENT '用户名',
                       email VARCHAR(100) UNIQUE COMMENT '邮箱',
                       phone VARCHAR(20) UNIQUE COMMENT '手机号',
                       password VARCHAR(255) NOT NULL COMMENT '密码',

    -- 个人信息
                       nickname VARCHAR(50) COMMENT '昵称',
                       avatar_url VARCHAR(255) COMMENT '头像URL',
                       gender TINYINT COMMENT '性别: 0-未知, 1-男, 2-女',
                       birthday DATE COMMENT '生日',
                       bio VARCHAR(200) COMMENT '个人简介',

    -- 会员信息
                       vip_level TINYINT UNSIGNED DEFAULT 0 COMMENT 'VIP等级: 0-普通，2-VIP会员，3-SVIP会员',
                       vip_start_at DATETIME COMMENT 'VIP开始时间'
  vip_expire_at DATETIME COMMENT 'VIP到期时间',

    -- 账户状态
                       account_status TINYINT DEFAULT 1 COMMENT '账户状态: 0-禁用, 1-正常，3-删除',

    -- 系统信息
                       last_login_at DATETIME COMMENT '最后登录时间',
                       last_login_ip VARCHAR(45) COMMENT '最后登录IP',
                       last_active_at DATETIME COMMENT '最后活跃时间',

    -- 时间戳
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       deleted_at DATETIME COMMENT '软删除时间',

    -- 索引
                       INDEX idx_email (email),
                       INDEX idx_phone (phone),
                       INDEX idx_username (username),
                       INDEX idx_account_status (account_status),
                       INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户主表';
