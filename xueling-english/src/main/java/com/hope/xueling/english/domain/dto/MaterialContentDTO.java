package com.hope.xueling.english.domain.dto;

import lombok.Data;

/**
 * 阅读材料详情数据传输对象
 * @author 谢光益
 * @since 2026/1/26
 */
@Data
public class MaterialContentDTO {
    /**
     * 阅读材料ID
     */
    private String materialId;

    /**
     * 章节ID
     */
    private String chapterId;

    /**
     * 材料内容
     */
    private String content;
}
