package com.hope.xueling.english.domain.dto;

import lombok.Data;
/**
 * 单词字典数据传输对象
 * @author 谢光湘
 * @since 2025/1/25
 */
@Data
public class WordDictionaryDTO {
    /**
     * 单词
     */
    private String word;
    /**
     * 单词含义
     */
    private String meaning;
    /**
     * 单词音标
     */
    private String phonetic;
}
