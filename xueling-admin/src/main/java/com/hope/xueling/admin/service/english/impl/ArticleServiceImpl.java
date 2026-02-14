package com.hope.xueling.admin.service.english.impl;

import com.hope.xueling.admin.domain.dto.ArticleDTO;
import com.hope.xueling.admin.domain.entity.Article;
import com.hope.xueling.admin.mapper.ArticleMapper;
import com.hope.xueling.admin.service.english.ArticleService;
import com.hope.xueling.common.ai.SmartReadingAssistant;
import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.common.util.AliOSSUtils;
import dev.langchain4j.model.image.ImageModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * 阅读服务实现类
 * @author 谢光益
 * @since 2026/1/31
 */
@Slf4j
@RequiredArgsConstructor
@Service("adminArticleService")
public class ArticleServiceImpl implements ArticleService {

    private final SmartReadingAssistant smartReadingAssistant;
    private final ImageModel zhipuImageModel;
    private final ArticleMapper articleMapper;
    private final AliOSSUtils aliOSSUtils;
    private  final ThreadPoolTaskExecutor myExecutor;

    @SneakyThrows // 抛出受检异常
    @Override
    public void uploadArticle(ArticleDTO articleDTO) {
        if(articleDTO == null) {
            throw new ValidationException("文章实体不能为空");
        }

        //获取articleDTO的content
        String contentStr = getArticleContent(articleDTO.getContent(), articleDTO.getTitle());
//        //将英文标题翻译成中文标题
//        String chineseTitle = smartReadingAssistant.translateTitle(articleDTO.getTitle());
//        log.info("文章标题："+chineseTitle);
//        //格式化文章
//        String content = smartReadingAssistant.formatEnglishArticle(articleDTO.getContent());
//        log.info("文章内容格式化："+content+"\n");
//        //文章中文翻译
//        String chineseMeaning = smartReadingAssistant.translateArticle(contentStr);
//        log.info("文章中文翻译："+chineseMeaning+"\n");
//        //生成文章的图片url
//        String imageUrl = generateArticleImage(contentStr, articleDTO.getTitle());
//        log.info("文章图片url："+imageUrl);
//        //生成词汇短语汇总
//        System.out.println("test:"+content);
//        String vocabularyPhrasesSummary = smartReadingAssistant.summarizeEnglishPhrases(articleDTO.getContent());
//        log.info("文章词汇短语汇总："+vocabularyPhrasesSummary);
//        //生成阅读文章的感悟
//        String articleInsights = smartReadingAssistant.generateArticleThoughts(contentStr);
//        log.info("文章感悟："+articleInsights);


        //        CompletableFuture<String> chineseTitle = CompletableFuture.runAsync((String)->{smartReadingAssistant.translateTitle(articleDTO.getTitle());},myExecutor);
//        //多线程获取中文标题，格式化文章，文章中文翻译，生成文章的图片url，生成词汇短语汇总，生成阅读文章的感悟
//        CompletableFuture<String> chineseTitleFuture = CompletableFuture.supplyAsync(() -> { return smartReadingAssistant.translateTitle(articleDTO.getTitle());}, myExecutor);
//        //CompletableFuture<String> contentFuture = CompletableFuture.supplyAsync(() -> { return smartReadingAssistant.formatEnglishArticle(contentStr);}, myExecutor);可简化
//        CompletableFuture<String> contentFuture = CompletableFuture.supplyAsync(() -> smartReadingAssistant.formatEnglishArticle(contentStr), myExecutor);
//        CompletableFuture<String> chineseMeaningFuture = CompletableFuture.supplyAsync(() -> smartReadingAssistant.translateArticle(contentStr), myExecutor);
//        CompletableFuture<String> vocabularyPhrasesSummaryFuture = CompletableFuture.supplyAsync(() -> smartReadingAssistant.summarizeEnglishPhrases(contentStr), myExecutor);
//        CompletableFuture<String> imageUrlFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                return generateArticleImage(contentStr, articleDTO.getTitle());
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            }
//        }, myExecutor);
//        CompletableFuture<String> articleInsightsFuture = CompletableFuture.supplyAsync(() -> smartReadingAssistant.generateArticleThoughts(contentStr), myExecutor);
//        //CompletableFuture.allOf: 将所有的CompletableFuture都绑在一起。
//        //.join(): 它会阻塞当前线程，直到调用它的 future 完成为止。
//        CompletableFuture.allOf(chineseTitleFuture, contentFuture, chineseMeaningFuture, vocabularyPhrasesSummaryFuture, imageUrlFuture, articleInsightsFuture).join();


        //多线程获取中文标题，格式化文章，文章中文翻译，生成文章的图片url，生成词汇短语汇总，生成阅读文章的感悟
        CompletableFuture<String> chineseTitleFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(">>> [Start] 开始翻译标题...");
            String result = smartReadingAssistant.translateTitle(articleDTO.getTitle());
            System.out.println(">>> [End] 结束翻译标题...");
            return result;
            }, myExecutor);
        //CompletableFuture<String> contentFuture = CompletableFuture.supplyAsync(() -> { return smartReadingAssistant.formatEnglishArticle(contentStr);}, myExecutor);可简化
        CompletableFuture<String> chineseMeaningFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(">>> [Start] 翻译文章...");
            String result = smartReadingAssistant.translateArticle(articleDTO.getContent());
            System.out.println(">>> [End] 结束翻译文章...");
            return result;
        }, myExecutor);
        CompletableFuture<String> vocabularyPhrasesSummaryFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(">>> [Start] 获取词汇短语汇总...");
            String result = smartReadingAssistant.summarizeEnglishPhrases(articleDTO.getContent());
            System.out.println(">>> [End] 结束获取词汇短语汇总...");
            return result;
        }, myExecutor);
        CompletableFuture<String> imageUrlFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(">>> [Start] 生成文章图片...");
                java.lang.String  result = generateArticleImage(contentStr, articleDTO.getTitle());
                System.out.println(">>> [End] 结束生成文章图片...");
                return result;
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }, myExecutor);
        CompletableFuture<String> articleInsightsFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(">>> [Start] 获取文章感悟...");
            String result = smartReadingAssistant.generateArticleThoughts(contentStr);
            System.out.println(">>> [End] 获取文章感悟...");
            return result;
        }, myExecutor);
        CompletableFuture<String> highlightAnalyzerFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(">>> [Start] 获取单词高亮分析...");
            String result = smartReadingAssistant.wordAnalyzer(articleDTO.getContent());
            System.out.println(">>> [End] 获取单词高亮分析...");
            return result;
        }, myExecutor);
        //CompletableFuture.allOf: 将所有的CompletableFuture都绑在一起。
        //.join(): 它会阻塞当前线程，直到调用它的 future 完成为止。
        CompletableFuture.allOf(chineseTitleFuture, chineseMeaningFuture, vocabularyPhrasesSummaryFuture, imageUrlFuture, articleInsightsFuture,highlightAnalyzerFuture).join();

        String chineseTitle = chineseTitleFuture.get();
        String chineseMeaning = chineseMeaningFuture.get();
        String vocabularyPhrasesSummary = vocabularyPhrasesSummaryFuture.get();
        String imageUrl = imageUrlFuture.get();
        String articleInsights = articleInsightsFuture.get();
        String highlights = highlightAnalyzerFuture.get();

        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setChineseTitle(chineseTitle);
        article.setContent(articleDTO.getContent());
        article.setChineseMeaning(chineseMeaning);
        article.setVocabularyPhrasesSummary(vocabularyPhrasesSummary);
        article.setImageUrl(imageUrl);
        article.setArticleInsights(articleInsights);
        article.setHighlights(highlights);
        articleMapper.insertArticle(article);

        log.info("上传文章成功^_^");
    }

    /**
     * 生成文章图片
     * @param content 文章内容
     * @return  图片url
     */
    private String generateArticleImage(String content, String imageName) throws MalformedURLException {
        //生成图片提示词
        String imagePrompt = smartReadingAssistant.generateImagePrompt(content);
        System.out.println("图片提示词：" + imagePrompt);
        //生成图片url
        URL imageUrl = new URL((zhipuImageModel.generate(imagePrompt)).content().url().toString());
        try (InputStream in = new BufferedInputStream(imageUrl.openStream())) {
            //上传文件到oss
            return aliOSSUtils.upload(in, imageName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文章内容
     * @param content 文章内容
     * @param title 文章标题
     * @return 文章内容
     */
    public String getArticleContent(String content, String title) {
        return "文章标题：" + title + "\n这是英语文章的内容；\n" + content;
    }
}
