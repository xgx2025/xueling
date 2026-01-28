package com.hope.xueling.english.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hope.xueling.common.exception.BusinessException;
import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.english.domain.dto.AddWordsToWordBookDTO;
import com.hope.xueling.english.domain.dto.CreateWordBookDTO;
import com.hope.xueling.english.domain.dto.RemoveWordsFromWordBookDTO;
import com.hope.xueling.english.domain.entity.WordBook;
import com.hope.xueling.english.domain.entity.WordBookDictionaryRelation;
import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.domain.vo.WordBookDetailVO;
import com.hope.xueling.english.domain.vo.WordBookVO;
import com.hope.xueling.english.domain.vo.WordDictionaryVO;
import com.hope.xueling.english.domain.vo.WordVO;
import com.hope.xueling.english.mapper.WordBookDictionaryRelationMapper;
import com.hope.xueling.english.mapper.WordBookMapper;
import com.hope.xueling.english.mapper.WordDictionaryMapper;
import com.hope.xueling.english.service.IWordBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 单词本服务实现类
 * @author 谢光湘
 * @since 2026/1/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WordBookServiceImpl implements IWordBookService {
    private final WordBookMapper wordBookMapper;
    private final WordDictionaryMapper wordDictionaryMapper;
    private final WordBookDictionaryRelationMapper wordBookDictionaryRelationMapper;

    // 定义允许的颜色列表（应与前端一致，或者放在配置中心/数据库）
    private static final Set<String> ALLOWED_COLORS = Set.of(
            "linear-gradient(120deg, #e0c3fc 0%, #8ec5fc 100%)",
            "linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)",
            "linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%)",
            "linear-gradient(120deg, #f6d365 0%, #fda085 100%)",
            "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
            "linear-gradient(120deg, #ff9a9e 0%, #fecfef 99%, #fecfef 100%)"
    );

    // 定义允许的图标列表
    private static final Set<String> ALLOWED_ICONS = Set.of(
            "📘", "📖", "📚", "📕", "📗", "📙", "🎓", "🗣️", "📝", "🧠", "🌟", "🔥"
    );



    @Override
    public void createWordBook(CreateWordBookDTO createWordBookDTO, Long userId) {
        // 1. 白名单校验
        if (!ALLOWED_COLORS.contains(createWordBookDTO.getColor())) {
            throw new ValidationException("不支持的封面颜色风格");
        }

        if (!ALLOWED_ICONS.contains(createWordBookDTO.getIcon())) {
            throw new ValidationException("不支持的图标类型");
        }
        WordBook wordBook = new WordBook();
        wordBook.setUserId(userId);
        wordBook.setId(IdUtil.getSnowflakeNextId());
        wordBook.setName(createWordBookDTO.getName());
        wordBook.setColor(createWordBookDTO.getColor());
        wordBook.setIcon(createWordBookDTO.getIcon());
        wordBook.setWordCount(0);
        wordBookMapper.insert(wordBook);
    }

    @Override
    public List<WordBookVO> getWordBooks(Long userId) {
        // 查询用户所有单词本
        QueryWrapper<WordBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("is_deleted", 0);
        List<WordBook> wordBooks = wordBookMapper.selectList(queryWrapper);
        if (wordBooks.isEmpty()) {
            return Collections.emptyList();
        }
        // 转换为VO
        //TODO: 计算掌握度(暂时写死)
        return wordBooks.stream().map(wordBook -> {
            WordBookVO wordBookVo = new WordBookVO();
            wordBookVo.setId(String.valueOf(wordBook.getId()));
            wordBookVo.setName(wordBook.getName());
            wordBookVo.setWordCount(wordBook.getWordCount());
            //TODO: 计算掌握度(暂时写死)
            wordBookVo.setMastery(45);
            return wordBookVo;
        }).toList();
    }

    @Override
    public WordBookDetailVO getWordBookDetail(Long wordBookId, Long userId) {
        // 检查单词本是否存在，用户是否对单词本有读写权限
        WordBook wordBook = checkWordBookExistsAndHasPermission(wordBookId, userId);
        // 构建VO
        WordBookDetailVO wordBookDetailVO = new WordBookDetailVO();
        wordBookDetailVO.setId(String.valueOf(wordBook.getId()));
        wordBookDetailVO.setName(wordBook.getName());
        wordBookDetailVO.setWordCount(wordBook.getWordCount());
        //TODO: 计算掌握度(暂时写死)
        wordBookDetailVO.setMastery(45);
        // 查询单词列表
        QueryWrapper<WordBookDictionaryRelation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.eq("word_book_id", wordBookId);
        List<WordBookDictionaryRelation> relations = wordBookDictionaryRelationMapper.selectList(relationQueryWrapper);
        if (relations.isEmpty()) {
            wordBookDetailVO.setWordList(Collections.emptyList());
        } else {
            // 批量查询单词详情
            QueryWrapper<WordDictionary> wordQueryWrapper = new QueryWrapper<>();
            wordQueryWrapper.in("id", relations.stream().map(WordBookDictionaryRelation::getWordId).toList());
            wordQueryWrapper.orderByDesc("create_time");
            List<WordDictionary> wordDictionaries = wordDictionaryMapper.selectList(wordQueryWrapper);
            //转换为VO
            wordBookDetailVO.setWordList(wordDictionaries.stream().map(wordDictionary -> {
                WordVO wordVO = new WordVO();
                wordVO.setId(String.valueOf(wordDictionary.getId()));
                wordVO.setWord(wordDictionary.getWord());
                wordVO.setMeaning(wordDictionary.getMeaning());
                wordVO.setPhonetic(wordDictionary.getPhonetic());
                wordVO.setMeaning(wordDictionary.getMeaning());
                wordVO.setCreateTime(wordDictionary.getCreateTime());
                return wordVO;
            }).toList());
        }
        return wordBookDetailVO;
    }


    @Override
    public List<WordDictionaryVO> matchWords(String words) {
        if (words == null || words.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<String> wordList = Arrays.stream(words.split(","))
                .map(String::trim)  // 去除首尾空格
                .filter(w -> !w.isEmpty())
                .distinct() // 去重，避免无效查询
                .toList();

        if (wordList.isEmpty()) return Collections.emptyList();

        QueryWrapper<WordDictionary> wrapper = new QueryWrapper<>();
        wrapper.and(qw -> {
            for (int i = 0; i < wordList.size(); i++) {
                if (i > 0) qw.or();
                qw.apply("UPPER(word) = UPPER({0})", wordList.get(i));
            }
        });

        List<WordDictionary> wordDictionaries = wordDictionaryMapper.selectList(wrapper);
        return wordDictionaries.stream().map(wordDictionary -> {
            WordDictionaryVO wordDictionaryVO = new WordDictionaryVO();
            wordDictionaryVO.setId(String.valueOf(wordDictionary.getId()));
            wordDictionaryVO.setWord(wordDictionary.getWord());
            wordDictionaryVO.setMeaning(wordDictionary.getMeaning());
            wordDictionaryVO.setPhonetic(wordDictionary.getPhonetic());
            wordDictionaryVO.setCreateTime(wordDictionary.getCreateTime());
            return wordDictionaryVO;
        }).toList();
    }

    @Transactional
    @Override
    public void addWordsToWordBook(AddWordsToWordBookDTO addWordsToWordBookDTO, Long userId) {
        Long wordBookId = Long.parseLong(addWordsToWordBookDTO.getWordBookId());
        List<Long> wordIds = addWordsToWordBookDTO.getWordIds().stream().map(Long::parseLong).toList();
        //单词数数量不超过20个
        if (wordIds.size() > 20) {
            log.warn("一次添加单词数量超过限制，用户ID：{}，单词本ID：{}，单词数量：{}", userId, wordBookId, wordIds.size());
            throw new ValidationException("一次最多添加20个单词");
        }
        // 检查单词本是否存在，用户是否对单词本有读写权限
        WordBook wordBook = checkWordBookExistsAndHasPermission(wordBookId, userId);
        // 找出还没有加入单词本的单词
        List<Long> needAddWordIds = new ArrayList<>();
        wordIds.forEach(wordId -> {
            QueryWrapper<WordBookDictionaryRelation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("word_book_id", wordBookId).eq("word_id", wordId);
            WordBookDictionaryRelation wordBookDictionaryRelation = wordBookDictionaryRelationMapper.selectOne(queryWrapper);
            if (wordBookDictionaryRelation == null) {
                needAddWordIds.add(wordId);
            }
        });
        List<WordBookDictionaryRelation> needAddRelations = needAddWordIds.stream().map(wordId -> {
            WordBookDictionaryRelation relation = new WordBookDictionaryRelation();
            relation.setId(IdUtil.getSnowflakeNextId());
            relation.setWordBookId(wordBookId);
            relation.setWordId(wordId);
            return relation;
        }).toList();
        // 批量添加单词到单词本
        wordBookDictionaryRelationMapper.insert(needAddRelations);
        // 更新单词本单词数量
        wordBook.setWordCount(wordBook.getWordCount() + needAddRelations.size());
        wordBookMapper.updateById(wordBook);
    }

    @Override
    public void deleteWordsFromWordBook(RemoveWordsFromWordBookDTO removeWordsFromWordBookDTO, Long userId) {
        Long wordBookId = Long.parseLong(removeWordsFromWordBookDTO.getWordBookId());
        List<Long> wordIds = removeWordsFromWordBookDTO.getWordIds().stream().map(Long::parseLong).toList();
        // 检查单词本是否存在，用户是否对单词本有读写权限
        WordBook wordBook = checkWordBookExistsAndHasPermission(wordBookId, userId);
        // 批量删除单词本中的单词
        QueryWrapper<WordBookDictionaryRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word_book_id", wordBookId).in("word_id", wordIds);
        wordBookDictionaryRelationMapper.delete(queryWrapper);
        // 更新单词本单词数量
        wordBook.setWordCount(wordBook.getWordCount() - wordIds.size());
        wordBookMapper.updateById(wordBook);
    }


    /**
     * 检查单词本是否存在，用户是否对单词本有读写权限
     */
    private WordBook checkWordBookExistsAndHasPermission(Long wordBookId, Long userId) {
        QueryWrapper<WordBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", wordBookId).eq("is_deleted", 0);
        WordBook wordBook = wordBookMapper.selectOne(queryWrapper);
        if (wordBook == null) {
            throw new BusinessException("单词本不存在");
        }
        // 检查用户是否对单词本有读写权限
        if (!wordBook.getUserId().equals(userId)) {
            throw new BusinessException("用户对单词本没有读写权限");
        }
        return wordBook;
    }
    //TODO
    /**
     * 计算单词本掌握度
     */

}
