package com.hope.xueling.english.controller;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.util.ThreadLocalUtils;
import com.hope.xueling.english.domain.dto.AddReadTimeDTO;
import com.hope.xueling.english.domain.dto.ReadTestDTO;
import com.hope.xueling.english.domain.vo.ArticleCategory;
import com.hope.xueling.english.domain.dto.ArticleSimple;
import com.hope.xueling.english.domain.vo.ArticleReadingStatusVO;
import com.hope.xueling.english.service.impl.ArticleServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.websocket.server.PathParam;
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
@RestController("userArticleController")
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
    public Result<List<ArticleSimple>> getArticlesByCategoryId(@RequestParam("categoryId") Long categoryId) {
        log.info("根据分类ID获取阅读文章列表，分类ID：{}", categoryId);
        List<ArticleSimple> articles = readService.getArticlesByCategoryId(categoryId);
        return Result.success(articles, "根据分类ID获取阅读文章列表成功");
    }

    /**
     * 根据文章ID获取文章翻译
     * @param articleId 文章ID
     * @return 文章翻译
     * 翻译文章内容格式：每个小标题前加##，段落之间用\n分隔，句子之间用&分隔
     */
    @GetMapping("/article/translation")
    public Result<String> getArticleTranslation(@RequestParam("articleId") Long articleId) {
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
     * 词汇短语汇总（用于用户上传的文章）
     * @param articleId 文章ID
     * @return 汇总结果
     */
    @GetMapping("/phrases")
    public Result<String> summarizeEnglishPhrases(@RequestParam("articleId") Long articleId) {
        log.info("词汇短语汇总，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String summarizeEnglishPhrases = readService.summarizeEnglishPhrases(userId,articleId);
        log.info("词汇短语汇总成功，汇总结果：{}", summarizeEnglishPhrases);
        return Result.success(summarizeEnglishPhrases, "词汇短语汇总成功");
    }

    /**
     * 获取阅读测试题
     * @param readTestDTO 阅读测试DTO
     * @return 测试题
     */
    @PostMapping("/test")
    public Result<String> readTest(@RequestBody ReadTestDTO readTestDTO) {
        log.info("获取阅读测试题中，文章ID：{}，难度：{}", readTestDTO.getArticleId(),readTestDTO.getDifficulty());
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String test = readService.readTest(userId,readTestDTO.getArticleId(),readTestDTO.getDifficulty());
        log.info("获取阅读测试题成功，测试题：{}", test );
        return Result.success(test, "阅读测试题成功");
    }

    /**
     * 重新生成阅读测试题
     * @param readTestDTO 阅读测试DTO
     * @return 测试题
     */
    @PostMapping("/retest")
    public Result<String> reReadTest(@RequestBody ReadTestDTO readTestDTO) {
        log.info("重新生成阅读测试题，文章ID：{}，难度：{}", readTestDTO.getArticleId(),readTestDTO.getDifficulty());
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        String test = readService.reReadTest(userId,readTestDTO.getArticleId(),readTestDTO.getDifficulty());
        return Result.success(test, "重新生成阅读测试题成功");
    }

    /**
     * 增加文章阅读时间（秒）
     * @param addReadTimeDTO 增加阅读时间DTO
     */
    @PostMapping("/readTime")
    public Result<String> addReadTime(@RequestBody AddReadTimeDTO addReadTimeDTO) {
        log.info("增加文章阅读时间，文章ID：{}，阅读时间：{}", addReadTimeDTO.getArticleId(),addReadTimeDTO.getReadTime());
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        readService.addReadTime(userId,addReadTimeDTO.getArticleId(),addReadTimeDTO.getReadTime());
        return Result.success("增加文章阅读时间成功");
    }

    /**
     * 获取文章阅读状态
     * @param articleId 文章ID
     * @return 文章阅读状态
     */
    @GetMapping("/status/{articleId}")
    public Result<ArticleReadingStatusVO> getArticleReadingStatus(@PathVariable("articleId") Long articleId) {
        log.info("获取文章阅读状态，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        ArticleReadingStatusVO articleReadingStatusVO = readService.getArticleReadingStatus(userId,articleId);
        return Result.success(articleReadingStatusVO, "获取文章阅读状态成功");
    }

    /**
     * 更新阅读进度为阅读中
     * @param articleId 文章ID
     */
    @PostMapping("/reading")
    public Result<String> updateReadingStatus(@RequestParam("articleId") Long articleId) {
        log.info("更新阅读进度为阅读中，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        readService.updateReadingStatus(userId,articleId);
        return Result.success("更新阅读进度为阅读中成功");
    }

    /**
     * 完成阅读文章（需要阅读时间超过2.5分钟）
     * @param articleId 文章ID
     * @return 完成结果
     */
    @PostMapping("/complete")
    public Result<String> completeReadArticle(@RequestParam("articleId") String articleId) {
        log.info("完成文章阅读，文章ID：{}", Long.valueOf(articleId));
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        readService.completeReadArticle(userId,Long.valueOf(articleId));
        return Result.success("完成文章阅读");
    }

    /**
     * 文章收藏
     * @param articleId 文章ID
     * @return 收藏结果
     */
    @PostMapping("/collect")
    public Result<String> collectArticle(@RequestParam("articleId") String articleId) {
        log.info("文章收藏，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        readService.collectArticle(userId,Long.valueOf(articleId));
        return Result.success("文章收藏成功");
    }

    /**
     * 取消文章收藏
     * @param articleId 文章ID
     * @return 取消收藏结果
     */
    @DeleteMapping("/cancelCollect")
    public Result<String> cancelCollectArticle(@RequestParam("articleId") String articleId) {
        log.info("取消文章收藏，文章ID：{}", articleId);
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        readService.cancelCollectArticle(userId,Long.valueOf(articleId));
        return Result.success("取消文章收藏成功");
    }

}
