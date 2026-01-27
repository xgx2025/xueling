package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 文章明细实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class Article {
    /**
     * 文章ID
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章段落数
     */
    private Integer paragraphCount;
}
