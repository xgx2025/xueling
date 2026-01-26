-- 阅读材料表（主表）
CREATE TABLE `material` (
                            `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '材料ID',
                            `title` varchar(255) NOT NULL COMMENT '标题',
                            `type` tinyint unsigned NOT NULL DEFAULT 1 COMMENT '类型（1：书籍，2：文章）',
                            `category` varchar(100) NOT NULL COMMENT '分类',
                            `tag` varchar(255) DEFAULT NULL COMMENT '标签（多个用逗号分隔）',
                            `is_free` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '是否免费（0：否，1：是）',
                            `author` varchar(100) NOT NULL COMMENT '作者',
                            `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_type` (`type`),
                            KEY `idx_category` (`category`),
                            KEY `idx_is_free` (`is_free`),
                            KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='阅读材料表';

-- 文章表（继承自材料表）
CREATE TABLE `article` (
                           `material_id` bigint unsigned NOT NULL COMMENT '材料ID',
                           `content` varchar(5000) NOT NULL COMMENT '内容',
                           `paragraph_count` int unsigned NOT NULL DEFAULT 0 COMMENT '段落数',
                           `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`material_id`),
                           CONSTRAINT `fk_article_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 书籍表（继承自材料表）
CREATE TABLE `book` (
                        `material_id` bigint unsigned NOT NULL COMMENT '材料ID',
                        `introduction` varchar(800) DEFAULT NULL COMMENT '书籍简介',
                        `chapter_count` int unsigned NOT NULL DEFAULT 0 COMMENT '章节数',
                        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`material_id`),
                        CONSTRAINT `fk_book_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍表';

-- 章节表（属于书籍）
CREATE TABLE `chapter` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '章节ID',
                           `book_id` bigint unsigned NOT NULL COMMENT '书籍ID（material_id）',
                           `title` varchar(255) NOT NULL COMMENT '章节标题',
                           `content` varchar(5000) NOT NULL COMMENT '章节内容',
                           `chapter_number` int unsigned NOT NULL COMMENT '章节序号',
                           `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uk_book_chapter` (`book_id`, `chapter_number`),
                           KEY `idx_book_id` (`book_id`),
                           CONSTRAINT `fk_chapter_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`material_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='章节表';

-- 文章段落翻译表
CREATE TABLE `article_translation` (
                                       `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '翻译ID',
                                       `article_id` bigint unsigned NOT NULL COMMENT '文章ID（material_id）',
                                       `paragraph_index` int unsigned NOT NULL COMMENT '段落序号',
                                       `chinese_meaning` varchar(5000) NOT NULL COMMENT '中文释义',
                                       `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `uk_article_paragraph` (`article_id`, `paragraph_index`),
                                       KEY `idx_article_id` (`article_id`),
                                       CONSTRAINT `fk_article_translation_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`material_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章段落翻译表';

-- 书籍段落翻译表
CREATE TABLE `book_translation` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '翻译ID',
                                    `chapter_id` bigint unsigned NOT NULL COMMENT '章节ID',
                                    `paragraph_index` int unsigned NOT NULL COMMENT '段落序号',
                                    `chinese_meaning` varchar(5000) NOT NULL COMMENT '中文释义',
                                    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_chapter_paragraph` (`chapter_id`, `paragraph_index`),
                                    KEY `idx_chapter_id` (`chapter_id`),
                                    CONSTRAINT `fk_book_translation_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍段落翻译表';

-- 用户阅读进度表
CREATE TABLE `reading_progress` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '进度ID',
                                    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                    `material_id` bigint unsigned NOT NULL COMMENT '材料ID',
                                    `progress_status` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '进度状态（0：未开始，1：阅读中，2：已读完）',
                                    `reading_duration` int unsigned NOT NULL DEFAULT 0 COMMENT '阅读时长（秒）',
                                    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_user_material` (`user_id`, `material_id`),
                                    KEY `idx_user_id` (`user_id`),
                                    KEY `idx_material_id` (`material_id`),
                                    KEY `idx_progress_status` (`progress_status`),
                                    CONSTRAINT `fk_reading_progress_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                                    CONSTRAINT `fk_reading_progress_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户阅读进度表';

-- 用户阅读状态表
CREATE TABLE `reading_status` (
                                  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                  `vocabulary_size` int unsigned NOT NULL DEFAULT 0 COMMENT '词汇量',
                                  `articles_read` int unsigned NOT NULL DEFAULT 0 COMMENT '已读文章数',
                                  `books_read` int unsigned NOT NULL DEFAULT 0 COMMENT '已读书籍数',
                                  `reading_days` int unsigned NOT NULL DEFAULT 0 COMMENT '阅读天数',
                                  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`user_id`),
                                  CONSTRAINT `fk_reading_status_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户阅读状态表';

-- 文章收藏表
CREATE TABLE `material_favorite` (
                            `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
                            `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                            `material_id` bigint unsigned NOT NULL COMMENT '材料ID',
                            `chapter_id` bigint unsigned DEFAULT NULL COMMENT '章节ID（仅对书籍有效）',
                            `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_user_favorite` (`user_id`, `material_id`, `chapter_id`),
                            KEY `idx_user_id` (`user_id`),
                            KEY `idx_material_id` (`material_id`),
                            KEY `idx_chapter_id` (`chapter_id`),
                            KEY `idx_created_at` (`created_at`),
                            CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `fk_favorite_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `fk_favorite_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章收藏表';
