CREATE TABLE `word_dictionary` (
                                   `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `word` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单词',
                                   `meaning` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '中文释义',
                                   `phonetic` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '音标（国际音标）',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_word` (`word`),
                                   KEY `idx_update_time` (`update_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词词典表';