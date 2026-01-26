package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 阅读材料实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class Material {
    /**
     * 阅读材料ID
     */
    private Long id;

    /**
     * 阅读材料标题
     */
    private String title;

    /**
     * 类型
     */
    private String type;

    /**
     * 分类ID
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
}
