package com.hope.xueling.english.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WordBookDictionaryRelation {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 单词书ID
     */
    private Long wordBookId;

    /**
     * 单词字典ID
     */
    private Long wordId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
