package com.hope.xueling.english.domain.entity;

import lombok.Data;

/**
 * 书籍段落翻译表
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class BookTranslation {
    /**
     * 书籍翻译ID
     */
    private Long id;

    /**
     * 章节ID
     */
    private Long chapterId;

    /**
     * 段落序号
     */
    private Integer paragraphIndex;

    /**
     * 中文翻译
     */
    private String chineseMeaning;
}
