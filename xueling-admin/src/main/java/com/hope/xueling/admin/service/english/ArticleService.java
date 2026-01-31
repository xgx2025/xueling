package com.hope.xueling.admin.service.english;

import com.hope.xueling.admin.domain.dto.ArticleDTO;

import java.net.MalformedURLException;

/**
 * 文章服务
 * @author 谢光益
 * @since 2026/1/31
 */
public interface ArticleService {
    /**
     * 上传文章
     * @param articleDTO 文章信息
     */
    void uploadArticle(ArticleDTO articleDTO) throws MalformedURLException;
}
