CREATE TABLE `word_book` (
                             `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `user_id` bigint unsigned NOT NULL COMMENT '所属用户ID',
                             `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单词本名称',
                             `color` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '封面背景色',
                             `icon` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '📘' COMMENT '封面图标',
                             `word_count` int unsigned NOT NULL DEFAULT '0' COMMENT '单词总数',
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0=否,1=是)',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                             PRIMARY KEY (`id`),
                             KEY `idx_is_deleted` (`is_deleted`),
                             KEY `idx_create_time` (`create_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词本表';
