package com.hope.xueling.admin.domain.entity;

import lombok.Data;

/**
 * 阅读文章实体类
 * @author 谢光益
 * @since 2026/1/31
 */
@Data
public class Article {
    /**
     * 文章标题
     */
    private String title;
    /**
     * 中文标题
     */
    private String chineseTitle;
    /**
     * 分类 ID
     */
    private String categoryId;
    /**
     * 标签
     */
    private String tag;
    /**
     * 作者
     */
    private String author;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 中文翻译
     */
    private String chineseMeaning;
    /**
     * 词汇短语汇总
     */
    private String vocabularyPhrasesSummary;
    /**
     * 图片url
     */
    private String imageUrl;
    /**
     * 文章感悟
     */
    private String articleInsights;
    /**
     * 是否免费
     */
    private Boolean isFree;
}
