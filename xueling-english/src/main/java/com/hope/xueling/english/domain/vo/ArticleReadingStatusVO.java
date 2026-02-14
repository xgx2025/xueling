package com.hope.xueling.english.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 阅读进度实体类
 * @author 谢光益
 * @since 2026/2/14
 */
@Builder
@Data
public class ArticleReadingStatusVO {
    /**
     * 文章 ID
     */
    private Long articleId;

    /**
     * 文章阅读状态
     */
    private Integer progressStatus;
}
