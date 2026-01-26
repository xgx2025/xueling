package com.hope.xueling.english.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WordBookVo {
    /**
     * 单词书ID
     */
    private Long id;

    /**
     * 单词书名称
     */
    private String name;

    /**
     * 单词总数
     */
    private Integer wordCount;

    /**
     * 掌握度（0-100）
     */
    private Integer masteryDegree;
}
