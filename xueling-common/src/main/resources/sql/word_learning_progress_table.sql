CREATE TABLE `word_learning_progress` (
                                          `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '学习进度ID',
                                          `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                          `word_id` bigint unsigned NOT NULL COMMENT '单词ID',
                                          `review_count` int unsigned DEFAULT '0' COMMENT '复习次数',
                                          `proficiency` int unsigned DEFAULT '0' COMMENT '熟练度数值',
                                          `familiarity_tag` tinyint unsigned DEFAULT '0' COMMENT '熟悉度标签(0=未学习,1=生疏,2=一般,3=熟悉,4=精通)',
                                          `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `uk_user_word` (`user_id`,`word_id`),
                                          KEY `idx_user_id` (`user_id`),
                                          KEY `idx_word_id` (`word_id`),
                                          KEY `idx_familiarity` (`familiarity_tag`),
                                          KEY `idx_user_familiarity` (`user_id`,`familiarity_tag`),
                                          KEY `idx_update_time` (`update_time` DESC),
                                          CONSTRAINT `fk_learning_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                          CONSTRAINT `fk_learning_word` FOREIGN KEY (`word_id`) REFERENCES `word_dictionary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词学习进度表';