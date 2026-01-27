package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 书籍简介实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class BookIntroduction {
    /**
     * 书籍ID(材料ID)
     */
    private Long bookId;

    /**
     * 书籍简介
     */
    private String introduction;

    /**
     * 书籍章节数
     */
    private Integer chapterCount;
}
