package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 文章翻译实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class ArticleTranslation {
    /**
     * 文章翻译ID
     */
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 段落ID
     */
    private Long paragraphId;

    /**
     * 段落序号
     */
    private Integer paragraphIndex;

    /**
     * 中文翻译
     */
    private String chineseMeaning;
}
