package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 书籍明细实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class Book {
    /**
     * 书籍ID
     */
    private Long id;

    /**
     * 书籍名称
     */
    private String name;

    /**
     * 书籍作者
     */
    private String author;

    /**
     * 书籍简介
     */
    private String introduction;

    /**
     * 书籍章节数
     */
    private int chapterCount;
}
