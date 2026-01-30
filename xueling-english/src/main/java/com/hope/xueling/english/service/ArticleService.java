package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.entity.Article;
import com.hope.xueling.english.domain.entity.ArticleCategory;

import java.util.List;

/**
 * 阅读服务接口
 * @author 谢光益
 * @since 2026/1/26
 */
public interface ArticleService {

    /**
     * 获取阅读文章分类列表
     * @return 阅读文章分类列表
     */
    List<ArticleCategory> getArticleCategories();

    /**
     * 根据分类ID获取阅读材料列表
     * @param categoryId 分类ID
     * @return 阅读材料列表
     */
    List<Article> getArticlesByCategoryId(String categoryId);

    /***
     * 获取文章翻译
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 文章翻译
     */
    String getArticleTranslation(Long userId, String articleId);

    /**
     * 句子分析
     * @param userId 用户ID
     * @param sentence 句子
     * @return 句子分析结果
     */
    String sentenceAnalysis(Long userId, String sentence);

    /**
     * 词汇短语汇总
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 汇总结果
     */
    String summarizeEnglishPhrases(Long userId, String articleId);

    /**
     * 阅读测试
     * @param userId 用户ID
     * @param articleId 文章ID
     * @param difficulty 难度
     * @return 测试结果
     */
    String readTest(Long userId, String articleId, String difficulty);

    /**
     * 重新阅读测试
     * @param userId 用户ID
     * @param articleId 文章ID
     * @param difficulty 难度
     * @return 测试结果
     */
    String reReadTest(Long userId, String articleId, String difficulty);
}
