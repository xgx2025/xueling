package com.hope.xueling.english.domain.vo;

import lombok.Data;

/**
 * 单词字典视图对象
 * @author 谢光湘
 * @since 2026/1/26
 */
@Data
public class WordDictionaryVO {
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
