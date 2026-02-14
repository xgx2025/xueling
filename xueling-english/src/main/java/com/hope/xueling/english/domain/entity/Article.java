package com.hope.xueling.english.domain.entity;

import lombok.Data;

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
     * 重点单词高亮分析
     */
    private String highlights;
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
