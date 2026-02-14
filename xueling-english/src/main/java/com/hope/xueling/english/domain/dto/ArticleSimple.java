package com.hope.xueling.english.domain.dto;

import lombok.Data;

/**
 * 文章明细实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class ArticleSimple {
    /**
     * 文章ID
     */
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 作者
     */
    private String author;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 中文标题
     */
     private String chineseTitle;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 高亮
     */
    private String highlights;

    /**
     * 文章感悟
     */
    private String articleInsights;
}

