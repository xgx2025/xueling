CREATE TABLE `word_review_record` (
                                      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                      `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                      `word_id` bigint unsigned NOT NULL COMMENT '单词ID',
                                      `review_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '复习时间',
                                      `review_result` tinyint(1) DEFAULT NULL COMMENT '复习结果(0=错误,1=正确)',
                                      `time_spent` int unsigned DEFAULT '0' COMMENT '花费时间(秒)',
                                      `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
                                      PRIMARY KEY (`id`),
                                      KEY `idx_user_id` (`user_id`),
                                      KEY `idx_word_id` (`word_id`),
                                      KEY `idx_review_time` (`review_time` DESC),
                                      KEY `idx_user_review_time` (`user_id`, `review_time` DESC),
                                      KEY `idx_user_word_review` (`user_id`, `word_id`, `review_time` DESC),
                                      CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                      CONSTRAINT `fk_review_word` FOREIGN KEY (`word_id`) REFERENCES `word_dictionary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词复习记录表';