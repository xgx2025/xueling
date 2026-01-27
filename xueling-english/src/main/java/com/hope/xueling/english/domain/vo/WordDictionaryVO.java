package com.hope.xueling.english.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 单词字典视图对象
 * @author 谢光湘
 * @since 2026/1/26
 */
@Data
@NoArgsConstructor
public class WordDictionaryVO {
    /**
     * 主键ID
     */
    private String id;
    /**
     * 单词
     */
    private String word;

    /**
     * 释义
     */
    private String meaning;

    /**
     * 音标
     */
    private String phonetic;
     /**
     * 是否在单词本中
     */
    private Boolean inWordBook;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
