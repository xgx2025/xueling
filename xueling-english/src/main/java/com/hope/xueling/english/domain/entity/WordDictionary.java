package com.hope.xueling.english.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 单词字典实体类
 * @author 谢光湘
 * @since 2025/1/25
 */
@Data
@NoArgsConstructor
public class WordDictionary {
    /**
     * 主键ID
     */
    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
