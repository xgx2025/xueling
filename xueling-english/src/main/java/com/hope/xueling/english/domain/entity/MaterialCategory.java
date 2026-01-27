package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 阅读材料分类实体类
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class MaterialCategory {
    /**
     * 阅读材料分类ID
     */
    private Long id;

     /**
      * 阅读材料分类名称
      */
    private String name;
}
