-- 文章分类表
CREATE TABLE `article_category` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                                    `name` varchar(100) NOT NULL COMMENT '分类名称',
                                    `sort_order` int unsigned NOT NULL DEFAULT 0 COMMENT '排序序号',
                                    `is_active` tinyint unsigned NOT NULL DEFAULT 1 COMMENT '是否启用（0：禁用，1：启用）',
                                    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_name` (`name`),
                                    KEY `idx_sort_order` (`sort_order`),
                                    KEY `idx_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';

-- 文章表
CREATE TABLE `article` (
                            `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '文章ID',
                            `title` varchar(255) NOT NULL COMMENT '标题',
                            `chinese_title` varchar(255) NOT NULL COMMENT '中文标题',
                            `category_id` bigint unsigned NOT NULL COMMENT '分类ID',
                            `tag` varchar(255) DEFAULT NULL COMMENT '标签（多个用逗号分隔）',
                            `author` varchar(100) NOT NULL COMMENT '作者',
                            `content` TEXT NOT NULL COMMENT '内容',
                            `highlights` JSON NOT NULL COMMENT '文章高亮',
                            `chinese_meaning` TEXT NOT NULL COMMENT '中文翻译',
                            `vocabulary_phrases_summary` text DEFAULT NULL COMMENT '词汇短语总结',
                            `article_insights` varchar(250) DEFAULT NULL COMMENT '文章感悟',
                            `image_url` varchar(250) DEFAULT NULL COMMENT '图片url',
                            `is_free` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '是否免费（0：否，1：是）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_category_id` (`category_id`),
                            KEY `idx_is_free` (`is_free`),
                            KEY `idx_create_time` (`create_time`),
                            CONSTRAINT `fk_material_category`
                                FOREIGN KEY (`category_id`)
                                    REFERENCES `article_category` (`id`)
                                    ON DELETE RESTRICT
                                    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

#新增文章表的文章高亮字段的sql语句
    ALTER TABLE `article` ADD COLUMN `highlights` JSON NOT NULL COMMENT '文章高亮' AFTER `content`;



-- 文章阅读进度表
CREATE TABLE `article_reading_progress` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '进度ID',
                                    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                    `article_id` bigint unsigned NOT NULL COMMENT '文章ID',
                                    `progress_status` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '进度状态（0：未开始，1：阅读中，2：已读完）',
                                    `reading_duration` int unsigned NOT NULL DEFAULT 0 COMMENT '阅读时长（秒）',
                                    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_user_article` (`user_id`, `article_id`),
                                    KEY `idx_user_id` (`user_id`),
                                    KEY `idx_article_id` (`article_id`),
                                    KEY `idx_progress_status` (`progress_status`),
                                    CONSTRAINT `fk_reading_progress_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                                    CONSTRAINT `fk_reading_progress_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE,
                                    CONSTRAINT `chk_reading_duration` CHECK (`reading_duration` >= 150)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户阅读进度表';

-- 用户阅读状态表
CREATE TABLE `reading_status` (
                                  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '阅读状态ID',
                                  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                  `vocabulary_size` int unsigned NOT NULL DEFAULT 0 COMMENT '词汇量',
                                  `articles_read` int unsigned NOT NULL DEFAULT 0 COMMENT '已读文章数',
                                  `books_read` int unsigned NOT NULL DEFAULT 0 COMMENT '已读书籍数',
                                  `reading_days` int unsigned NOT NULL DEFAULT 0 COMMENT '阅读天数',
                                  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`),
                                  CONSTRAINT `fk_reading_status_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户阅读状态表';

-- 文章收藏表
CREATE TABLE `article_favorite` (
                            `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
                            `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                            `article_id` bigint unsigned NOT NULL COMMENT '文章ID',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_user_favorite` (`user_id`, `article_id`),
                            KEY `idx_user_id` (`user_id`),
                            KEY `idx_article_id` (`article_id`),
                            KEY `idx_create_time` (`create_time`),
                            CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `fk_favorite_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章收藏表';


-- 用户文章测试题表
CREATE TABLE `article_reading_test` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '测试题ID',
                                    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
                                    `article_id` bigint unsigned NOT NULL COMMENT '文章ID',
                                    `difficulty` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '测试难度（0：简单，1：中等，2：困难）',
                                    `content` varchar(10000) NOT NULL COMMENT '测试内容',
                                    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_user_article_test` (`user_id`, `article_id`),
                                    KEY `idx_user_id` (`user_id`),
                                    KEY `idx_article_id` (`article_id`),
                                    CONSTRAINT `fk_reading_test_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                                    CONSTRAINT `fk_reading_test_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户文章测试题表';


# -- 书籍表
# CREATE TABLE `book` (
#                         `material_id` bigint unsigned NOT NULL COMMENT '材料ID',
#                         `introduction` varchar(800) DEFAULT NULL COMMENT '书籍简介',
#                         `chapter_count` int unsigned NOT NULL DEFAULT 0 COMMENT '章节数',
#                         `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#                         `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#                         PRIMARY KEY (`material_id`),
#                         CONSTRAINT `fk_book_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍表';
#
# -- 章节表（属于书籍）
# CREATE TABLE `chapter` (
#                            `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '章节ID',
#                            `book_id` bigint unsigned NOT NULL COMMENT '书籍ID（material_id）',
#                            `title` varchar(255) NOT NULL COMMENT '章节标题',
#                            `content` varchar(5000) NOT NULL COMMENT '章节内容',
#                            `chapter_index` int unsigned NOT NULL COMMENT '章节序号',
#                            `paragraph_count` int unsigned NOT NULL DEFAULT 0 COMMENT '段落数',
#                            `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#                            `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#                            PRIMARY KEY (`id`),
#                            UNIQUE KEY `uk_book_chapter` (`book_id`, `chapter_index`),
#                            KEY `idx_book_id` (`book_id`),
#                            CONSTRAINT `fk_chapter_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`material_id`) ON DELETE CASCADE
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='章节表';

# -- 书籍段落翻译表
# CREATE TABLE `book_translation` (
#                                     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '翻译ID',
#                                     `chapter_id` bigint unsigned NOT NULL COMMENT '章节ID',
#                                     `paragraph_index` int unsigned NOT NULL COMMENT '段落序号',
#                                     `chinese_meaning` varchar(5000) NOT NULL COMMENT '中文释义',
#                                     `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#                                     `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#                                     PRIMARY KEY (`id`),
#                                     UNIQUE KEY `uk_chapter_paragraph` (`chapter_id`, `paragraph_index`),
#                                     KEY `idx_chapter_id` (`chapter_id`),
#                                     CONSTRAINT `fk_book_translation_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`) ON DELETE CASCADE
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍段落翻译表';
