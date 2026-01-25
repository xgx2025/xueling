CREATE TABLE `word_book_dictionary_relation` (
                                                 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                                 `word_book_id` bigint unsigned NOT NULL COMMENT '单词本ID',
                                                 `word_id` bigint unsigned NOT NULL COMMENT '单词ID',
                                                 `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0=无效,1=有效)',
                                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE KEY `uk_book_word` (`word_book_id`, `word_id`),
                                                 KEY `idx_word_book_id` (`word_book_id`),
                                                 KEY `idx_word_id` (`word_id`),
                                                 KEY `idx_status` (`status`),
                                                 KEY `idx_book_status` (`word_book_id`, `status`),
                                                 CONSTRAINT `fk_word_book_dict_book` FOREIGN KEY (`word_book_id`) REFERENCES `word_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                 CONSTRAINT `fk_word_book_dict_word` FOREIGN KEY (`word_id`) REFERENCES `word_dictionary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词本-词典关系表';
