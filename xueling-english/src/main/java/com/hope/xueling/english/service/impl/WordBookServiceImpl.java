package com.hope.xueling.english.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hope.xueling.common.exception.BusinessException;
import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.english.domain.entity.WordBook;
import com.hope.xueling.english.domain.entity.WordBookDictionaryRelation;
import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.domain.vo.WordBookVo;
import com.hope.xueling.english.mapper.WordBookDictionaryRelationMapper;
import com.hope.xueling.english.mapper.WordBookMapper;
import com.hope.xueling.english.mapper.WordDictionaryMapper;
import com.hope.xueling.english.service.IWordBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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



    @Override
    public void createWordBook(String name, Long userId) {
        WordBook wordBook = new WordBook();
        wordBook.setUserId(userId);
        wordBook.setId(IdUtil.getSnowflakeNextId());
        wordBook.setName(name);
        wordBook.setWordCount(0);
        wordBookMapper.insert(wordBook);
    }

    @Override
    public List<WordBookVo> getWordBooks(Long userId) {
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
            WordBookVo wordBookVo = new WordBookVo();
            wordBookVo.setId(wordBook.getId());
            wordBookVo.setName(wordBook.getName());
            wordBookVo.setWordCount(wordBook.getWordCount());
            //TODO: 计算掌握度(暂时写死)
            wordBookVo.setMasteryDegree(45);
            return wordBookVo;
        }).toList();
    }


    @Override
    public List<WordDictionary> matchWords( String words) {
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

        return wordDictionaryMapper.selectList(wrapper);
    }

    @Transactional
    @Override
    public void addWordsToWordBook(Long wordBookId, Long userId, List<Long> wordIds) {
        //单词数数量不超过20个
        if (wordIds.size() > 20) {
            log.warn("一次添加单词数量超过限制，用户ID：{}，单词本ID：{}，单词数量：{}", userId, wordBookId, wordIds.size());
            throw new ValidationException("一次最多添加20个单词");
        }
        // 检查单词本是否存在
        WordBook wordBook = wordBookMapper.selectById(wordBookId);
        if (wordBook == null) {
            throw new BusinessException("单词本不存在");
        }
        // 检查用户是否对单词本有写权限
        if (!wordBook.getUserId().equals(userId)) {
            throw new BusinessException("用户对单词本没有写权限");
        }
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

}
