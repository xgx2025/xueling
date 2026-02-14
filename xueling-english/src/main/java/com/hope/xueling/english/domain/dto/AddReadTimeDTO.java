package com.hope.xueling.english.domain.dto;

import lombok.Data;

@Data
public class AddReadTimeDTO {

    /**
     * 文章ID
     */
     private Long articleId;

    /**
     * 阅读时间（秒）
     */
     private Integer readTime;
}
