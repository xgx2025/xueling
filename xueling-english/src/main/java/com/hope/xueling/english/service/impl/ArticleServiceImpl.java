package com.hope.xueling.english.service.impl;

import com.hope.xueling.common.ai.SmartReadingAssistant;
import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.common.mapper.UserMapper;
import com.hope.xueling.english.domain.entity.Article;
import com.hope.xueling.english.domain.entity.ArticleCategory;
import com.hope.xueling.english.mapper.ArticleMapper;
import com.hope.xueling.english.mapper.TestMapper;
import com.hope.xueling.english.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 阅读服务实现类
 * @author 谢光益
 * @since 2026/1/26
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final TestMapper testMapper;
    private final SmartReadingAssistant smartReadingAssistant;

    @Override
    public List<ArticleCategory> getArticleCategories() {
        return articleMapper.selectArticleCategories();
    }

    @Override
    public List<Article> getArticlesByCategoryId(String categoryId) {
        //检查分类ID是否为空
        if (categoryId == null || categoryId.isEmpty()) {
            throw new ValidationException("分类 ID 不能为空");
        }
        List<Article> articles = articleMapper.selectArticlesByCategoryId(categoryId);
        return articles;
    }

    @Override
    public String getArticleTranslation(Long userId, String articleId) {
        if (userId == null || articleId == null || articleId.isEmpty()) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        String articleTranslation = articleMapper.selectArticleTranslation(userId, articleId);
        if (articleTranslation == null) {
            throw new ValidationException("文章翻译不存在");
        }
        return articleTranslation;
    }

    @Override
    public String sentenceAnalysis(Long userId, String sentence) {
        if (userId == null || sentence == null || sentence.isEmpty()) {
            throw new ValidationException("用户ID和句子不能为空");
        }
        //检查用户是否是会员
        if (userMapper.isVip(userId) == null) {
            throw new ValidationException("用户不是会员，不能使用句子分析功能");
        }
        String analysisResult = smartReadingAssistant.analyzeEnglishSentence(sentence);
        if (analysisResult == null) {
            throw new ValidationException("句子分析结果不存在");
        }
        return analysisResult;
    }

    @Override
    public String summarizeEnglishPhrases(Long userId, String articleId) {
        if (userId == null || articleId == null || articleId.isEmpty()) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //检查用户是否是会员,并从数据库获取词汇短语汇总结果
        if (userMapper.isVip(userId) == null) {
            throw new ValidationException("用户不是会员，不能使用词汇短语汇总功能");
        }
        //先从数据库获取词汇短语汇总结果
        Map<Long, String> articlePhrases = articleMapper.selectArticlePhrases(articleId);
        if (articlePhrases == null || articlePhrases.isEmpty()) {
            //获取文章内容
            String content = getArticleContentForAi(articleId);
            //调用大模型进行词汇短语汇总
            String summarizeEnglishPhrases = smartReadingAssistant.summarizeEnglishPhrases(content);
            //更新数据库中的词汇短语汇总结果
            articleMapper.updateArticlePhrases(articleId, summarizeEnglishPhrases);
            return summarizeEnglishPhrases;
        }
        //返回词汇短语汇总结果
        return articlePhrases.get(userId);
    }

    @Override
    public String readTest(Long userId, String articleId, String difficulty) {
        if (userId == null || articleId == null || articleId.isEmpty() || difficulty == null || difficulty.isEmpty()) {
            throw new ValidationException("用户ID、文章ID和难度不能为空");
        }
        if (userMapper.isVip(userId) == null) {
            throw new ValidationException("用户不是会员，不能使用阅读测试功能");
        }
        //先从数据库获取阅读测试题
        String readTest = testMapper.selectReadTest(userId, articleId);
        if (readTest == null) {
            //获取文章内容
            String content = getArticleContentForAi(articleId);
            //调用大模型生成测试题
            readTest = smartReadingAssistant.generateReadingTest(content + " 测试难度：" + difficulty);
            //更新数据库中的测试题
            testMapper.updateReadTest(userId, articleId, readTest);
        }
        return readTest;
    }

    @Override
    public String reReadTest(Long userId, String articleId, String difficulty) {
        if (userId == null || articleId == null || articleId.isEmpty() || difficulty == null || difficulty.isEmpty()) {
            throw new ValidationException("用户ID、文章ID和难度不能为空");
        }
        if (userMapper.isVip(userId) == null) {
            throw new ValidationException("用户不是会员，不能使用重新阅读测试功能");
        }
        //获取文章内容
        String content = getArticleContentForAi(articleId);
        //调用大模型生成测试题
        String reReadTest = smartReadingAssistant.generateReadingTest(content + " 测试难度：" + difficulty);
        //更新数据库中的测试题
        testMapper.updateReadTest(userId, articleId, reReadTest);
        return reReadTest;
    }

    /**
     * 获取阅读文章内容
     * @param articleId 文章ID
     * @return 文章内容
     */
    public String getArticleContentForAi(String articleId) {
        if (articleId == null || articleId.isEmpty()) {
            throw new ValidationException("文章ID不能为空");
        }
        String tile = articleMapper.selectTitleById(articleId);
        String content = articleMapper.selectContentById(articleId);
        if (content == null) {
            throw new ValidationException("文章内容不存在");
        }
        return "文章的格式：每个小标题前有##，段落之间用\\n分隔，句子之间用&分隔\n文章标题：" + tile + "\n文章的内容；\n" + content;
    }
}
