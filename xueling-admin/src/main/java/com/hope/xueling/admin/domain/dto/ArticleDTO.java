package com.hope.xueling.admin.domain.dto;

import lombok.Data;

/**
 * 文章数据传输对象
 * @author 谢光益
 * @since 2026/1/31
 */
@Data
public class ArticleDTO {
    /**
     * 文章标题
     */
    private String title;
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
     * 是否免费
     */
    private Boolean isFree;
}
