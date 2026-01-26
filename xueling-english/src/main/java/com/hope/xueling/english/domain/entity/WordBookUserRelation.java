package com.hope.xueling.english.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 单词本用户关系表
 * @author 谢光湘
 * @since 2026/1/25
 */
@Data
@TableName("word_book_user_relation")
public class WordBookUserRelation {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 单词本ID
     */
    private Long wordBookId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 状态 0-无效 1-有效
     */
    private int status;
     /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
