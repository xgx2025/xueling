package com.hope.xueling.english.controller;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.util.ThreadLocalUtils;
import com.hope.xueling.english.domain.entity.Article;
import com.hope.xueling.english.domain.entity.ArticleCategory;
import com.hope.xueling.english.service.impl.ArticleServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 阅读控制器
 * @author 谢光益
 * @since 2026/1/26
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/read")
public class ArticleController {

    //注入服务实现类
    private final ArticleServiceImpl readService;

    /**
     * 获取阅读文章分类列表
     * @return 阅读文章分类列表
     */
    @GetMapping("/categories")
    public Result<List<ArticleCategory>> getArticleCategories() {
        log.info("获取阅读文章分类列表");
        List<ArticleCategory> articleCategories = readService.getArticleCategories();
        return Result.success(articleCategories, "获取阅读文章分类列表成功");
    }

    /**
     * 根据分类ID获取阅读文章列表
     * @param categoryId 分类ID
     * @return 阅读文章列表
     */
    @GetMapping("/articles")
    public Result<List<Article>> getArticlesByCategoryId(@RequestParam("categoryId") String categoryId) {
        log.info("根据分类ID获取阅读文章列表，分类ID：{}", categoryId);
        List<Article> articles = readService.getArticlesByCategoryId(categoryId);
        return Result.success(articles, "根据分类ID获取阅读文章列表成功");
    }

    /**
     * 根据文章ID获取文章翻译
     * @param articleId 文章ID
     * @return 文章翻译
     * 翻译文章内容格式：TODO
     */
    @GetMapping("/article/translation")
    public Result<String> getArticleTranslation(@RequestParam("articleId") String articleId) {
        log.info("根据文章ID获取文章翻译，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String articleTranslation = readService.getArticleTranslation(userId,articleId);
        return Result.success(articleTranslation, "根据文章ID获取文章翻译成功");
    }

    /**
     * 句子分析
     * @param sentence 句子
     * @return 句子分析结果
     */
    @GetMapping("/sentence")
    public Result<String> sentenceAnalysis(@RequestParam("sentence") String sentence) {
        log.info("句子分析，句子：{}", sentence);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String sentenceAnalysis = readService.sentenceAnalysis(userId,sentence);
        return Result.success(sentenceAnalysis, "句子分析成功");
    }

    /**
     * 词汇短语汇总
     * @param articleId 文章ID
     * @return 汇总结果
     */
    @GetMapping("/phrases")
    public Result<String> summarizeEnglishPhrases(@RequestParam("articleId") String articleId) {
        log.info("词汇短语汇总，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String summarizeEnglishPhrases = readService.summarizeEnglishPhrases(userId,articleId);
        return Result.success(summarizeEnglishPhrases, "词汇短语汇总成功");
    }

    /**
     * 阅读测试题
     * @param articleId 文章ID
     * @param difficulty 难度
     * @return 测试题
     */
    @GetMapping("/test")
    public Result<String> readTest(@RequestParam("articleId") String articleId,@RequestParam("difficulty") String difficulty) {
        log.info("阅读测试题，文章ID：{}，难度：{}", articleId,difficulty);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String test = readService.readTest(userId,articleId,difficulty);
        return Result.success(test, "阅读测试题成功");
    }

    /**
     * 重新生成阅读测试题
     * @param articleId 文章ID
     * @param difficulty 难度
     * @return 测试题
     */
    @PostMapping("/test")
    public Result<String> reReadTest(@RequestParam("articleId") String articleId,@RequestParam("difficulty") String difficulty) {
        log.info("重新生成阅读测试题，文章ID：{}，难度：{}", articleId,difficulty);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String test = readService.reReadTest(userId,articleId,difficulty);
        return Result.success(test, "重新生成阅读测试题成功");
    }


}
