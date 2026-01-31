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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

    @Override
    public void uploadArticle(ArticleDTO articleDTO) throws MalformedURLException {
        if(articleDTO == null) {
            throw new ValidationException("文章实体不能为空");
        }
        //将英文标题翻译成中文标题
        String chineseTitle = smartReadingAssistant.translateTitle(articleDTO.getTitle());
        log.info("文章标题："+chineseTitle);
        //格式化文章
        String content = smartReadingAssistant.formatEnglishArticle(articleDTO.getContent());
        log.info("文章内容格式化："+content);
        //获取articleDTO的content
        String contentStr = getArticleContent(articleDTO.getContent(), articleDTO.getTitle());
        //文章中文翻译
        String chineseMeaning = smartReadingAssistant.translateArticle(contentStr);
        log.info("文章中文翻译："+chineseMeaning);

        //生成文章的图片url
        String imageUrl = generateArticleImage(contentStr, articleDTO.getTitle());
        log.info("文章图片url："+imageUrl);
        //生成词汇短语汇总
        String vocabularyPhrasesSummary = smartReadingAssistant.summarizeEnglishPhrases(content);
        log.info("文章词汇短语汇总："+vocabularyPhrasesSummary);
        //生成阅读文章的感悟
        String articleInsights = smartReadingAssistant.generateArticleThoughts(contentStr);
        log.info("文章感悟："+articleInsights);

        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setChineseTitle(chineseTitle);
        article.setContent(content);
        article.setChineseMeaning(chineseMeaning);
        article.setVocabularyPhrasesSummary(vocabularyPhrasesSummary);
        article.setImageUrl(imageUrl);
        article.setArticleInsights(articleInsights);
        articleMapper.insertArticle(article);
    }

    /**
     * 生成文章图片
     * @param content 文章内容
     * @return  图片url
     */
    private String generateArticleImage(String content, String imageName) throws MalformedURLException {
        //生成图片提示词
        String imagePrompt = smartReadingAssistant.generateImagePrompt(content);
        System.out.println(imagePrompt);
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
        return "这是英语文章的格式：每个小标题前有##，段落之间用\\n分隔，句子之间用&分隔\n文章标题：" + title + "\n这是英语文章的内容；\n" + content;
    };
}
