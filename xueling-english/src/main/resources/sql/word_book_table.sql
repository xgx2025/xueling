CREATE TABLE `word_book` (
                             `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单词本名称',
                             `word_count` int unsigned NOT NULL DEFAULT '0' COMMENT '单词总数',
                             `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认(0=否,1=是)',
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0=否,1=是)',
                             `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
                             `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                             PRIMARY KEY (`id`),
                             KEY `idx_is_default` (`is_default`),
                             KEY `idx_is_deleted` (`is_deleted`),
                             KEY `idx_insert_time` (`insert_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词本表';
