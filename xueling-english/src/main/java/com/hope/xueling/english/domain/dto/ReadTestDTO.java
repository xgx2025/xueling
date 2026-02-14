package com.hope.xueling.english.domain.dto;

import lombok.Data;

@Data
public class ReadTestDTO {
    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 难度
     */
    private Integer difficulty;
}
