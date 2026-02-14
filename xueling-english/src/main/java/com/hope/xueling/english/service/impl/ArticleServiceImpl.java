package com.hope.xueling.english.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hope.xueling.common.ai.SmartReadingAssistant;
import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.common.mapper.UserMapper;
import com.hope.xueling.english.domain.entity.Article;
import com.hope.xueling.english.domain.vo.ArticleCategory;
import com.hope.xueling.english.domain.dto.ArticleSimple;
import com.hope.xueling.english.domain.vo.ArticleReadingStatusVO;
import com.hope.xueling.english.mapper.ArticleFavoriteMapper;
import com.hope.xueling.english.mapper.ArticleMapper;
import com.hope.xueling.english.mapper.ReadingProgressMapper;
import com.hope.xueling.english.mapper.TestMapper;
import com.hope.xueling.english.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private final ReadingProgressMapper readingProgressMapper;
    private final ArticleFavoriteMapper articleFavoriteMapper;
    private final SmartReadingAssistant smartReadingAssistant;

    @Override
    public List<ArticleCategory> getArticleCategories() {
        return articleMapper.selectArticleCategories();
    }

    @Override
    public List<ArticleSimple> getArticlesByCategoryId(Long categoryId) {
        //检查分类ID是否为空
        if (categoryId == null) {
            throw new ValidationException("分类 ID 不能为空");
        }
        List<ArticleSimple> articles = articleMapper.selectArticlesByCategoryId(categoryId);
        return articles;
    }

    @Override
    public String getArticleTranslation(Long userId, Long articleId) {
        if (userId == null || articleId == null) {
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
    public String summarizeEnglishPhrases(Long userId, Long articleId) {
        if (userId == null || articleId == null) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //检查用户是否是会员,并从数据库获取词汇短语汇总结果
        if (userMapper.isVip(userId) == null) {
            throw new ValidationException("用户不是会员，不能使用词汇短语汇总功能");
        }
        //先从数据库获取词汇短语汇总结果
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", articleId).select("id", "vocabulary_phrases_summary");
        Article article = articleMapper.selectOne(queryWrapper);
        log.info("数据库中的词汇短语汇总结果：{}", article);
        Map<Long, String> articlePhrases = new HashMap<>();
        articlePhrases.put(articleId, article.getVocabularyPhrasesSummary());
        //如果不存在，则调用大模型进行词汇短语汇总
        if (articlePhrases == null || articlePhrases.isEmpty()) {
            //获取文章内容
            String content = getArticleContentForAi(articleId);
            //调用大模型进行词汇短语汇总
            String summarizeEnglishPhrases = smartReadingAssistant.summarizeEnglishPhrases(content);
            //更新数据库中的词汇短语汇总结果
            articleMapper.updateArticlePhrases(articleId, summarizeEnglishPhrases);
            log.info("词汇短语汇总结果：{}", summarizeEnglishPhrases);
            return summarizeEnglishPhrases;
        }
        //返回词汇短语汇总结果
        log.info("词汇短语汇总结果：{}", articlePhrases.get(articleId));
        return articlePhrases.get(articleId);
    }

    @Override
    public String readTest(Long userId, Long articleId, Integer difficulty) {
        if (userId == null || articleId == null || difficulty == null || difficulty < 0) {
            throw new ValidationException("用户ID、文章ID和难度不能为空");
        }
        if (userMapper.isVip(userId) == null) {
            throw new ValidationException("用户不是会员，不能使用阅读测试功能");
        }
        //先从数据库获取阅读测试题
        String readTest = testMapper.selectReadTest(userId, articleId, difficulty);

        if (readTest == null) {
            //获取文章内容
            String content = getArticleContentForAi(articleId);
            //调用大模型生成测试题
            String readTestContent = smartReadingAssistant.generateReadingTest(content + " 测试难度：" + difficulty);
            //如果测试题不存在，新增测试题
            if(testMapper.selectIdByArticleIdAndUserId(articleId, userId) == null) {
                testMapper.insertReadTest(userId, articleId, readTestContent, difficulty);
            } else {
                //如果测试id存在，更新数据库中的测试题
                testMapper.updateReadTest(userId, articleId, readTestContent, difficulty);
            }
            return readTestContent;
        }
        //返回测试题内容
        log.info("测试题内容：{}", readTest);
        return readTest;
    }

    @Override
    public String reReadTest(Long userId, Long articleId, Integer difficulty) {
        if (userId == null || articleId == null || difficulty == null || difficulty < 0) {
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
        testMapper.updateReadTest(userId, articleId, reReadTest, difficulty);
        return reReadTest;
    }

    @Override
    public void collectArticle(Long userId, Long articleId) {
        if (userId == null || articleId == null) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //检查用户是否阅读完文章
        if (readingProgressMapper.selectReadStatus(userId, articleId) == null) {
            throw new ValidationException("用户未阅读完文章，不能收藏文章");
        }

        //更新数据库中的文章收藏
        articleFavoriteMapper.updateCollectArticle(userId, articleId);
    }

    @Override
    public void updateReadingStatus(Long userId, Long articleId) {
        if(userId == null || articleId == null) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //更新数据库中的阅读进度为阅读中
        readingProgressMapper.updateReadingStatus(userId, articleId);
    }

    @Override
    public void completeReadArticle(Long userId, Long articleId) {
        if(userId == null || articleId == null) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //检查用户是否阅读时间是否超过2.5分钟(150秒)
        if (
                readingProgressMapper.selectReadTime(userId, articleId) < 150) {
            throw new ValidationException("总阅读时间不足2.5分钟，不能完成阅读");
        }
        //更新数据库中的阅读进度
        readingProgressMapper.updateReadStatus(userId, articleId);
    }

    @Override
    public void addReadTime(Long userId, Long articleId, Integer readTime) {
        if(userId == null || articleId == null || readTime == null || readTime < 0) {
            throw new ValidationException("用户ID、文章ID和阅读时间不能为空");
        }

        //时间要大于等于150秒
        if (readTime < 150) {
            throw new ValidationException("阅读时间必须大于等于150秒");
        }

        //检查是否已经存在阅读时间
        if (readingProgressMapper.selectReadTime(userId, articleId) == null) {
            //新增数据库中的阅读时间
            readingProgressMapper.addReadTime(userId, articleId, readTime);
        }

        //更新数据库中的阅读时间
        readingProgressMapper.updateReadTime(userId, articleId, readTime);

    }

    @Override
    public void cancelCollectArticle(Long userId, Long articleId) {
        if(userId == null || articleId == null) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //更新数据库中的文章收藏
        articleFavoriteMapper.deleteCollectArticle(userId, articleId);
    }

    @Override
    public ArticleReadingStatusVO getArticleReadingStatus(Long userId, Long articleId) {
        if (userId == null || articleId == null) {
            throw new ValidationException("用户ID和文章ID不能为空");
        }
        //获取数据库中的阅读进度
        Integer progressStatus = readingProgressMapper.selectReadingStatus(userId, articleId);
        return ArticleReadingStatusVO.builder()
                .articleId(articleId)
                .progressStatus(progressStatus)
                .build();
    }

    /**
     * 获取阅读文章内容
     * @param articleId 文章ID
     * @return 文章内容
     */
    public String getArticleContentForAi(Long articleId) {
        if (articleId == null) {
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
