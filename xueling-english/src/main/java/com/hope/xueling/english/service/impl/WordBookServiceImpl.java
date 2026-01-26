package com.hope.xueling.english.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hope.xueling.common.exception.BusinessException;
import com.hope.xueling.english.domain.entity.WordBook;
import com.hope.xueling.english.domain.entity.WordBookDictionaryRelation;
import com.hope.xueling.english.domain.entity.WordBookUserRelation;
import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.mapper.WordBookDictionaryRelationMapper;
import com.hope.xueling.english.mapper.WordBookMapper;
import com.hope.xueling.english.mapper.WordBookUserRelationMapper;
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
    private final WordBookUserRelationMapper wordBookUserRelationMapper;
    private final WordBookDictionaryRelationMapper wordBookDictionaryRelationMapper;



    @Transactional
    @Override
    public void createWordBook(String name, Long userId) {
        Long id = IdUtil.getSnowflakeNextId();
        WordBook wordBook = new WordBook();
        wordBook.setId(id);
        wordBook.setName(name);
        wordBook.setWordCount(0);
        wordBookMapper.insert(wordBook);
        WordBookUserRelation wordBookUserRelation = new WordBookUserRelation();
        wordBookUserRelation.setId(IdUtil.getSnowflakeNextId());
        wordBookUserRelation.setWordBookId(id);
        wordBookUserRelation.setUserId(userId);
        wordBookUserRelation.setStatus(1);
        wordBookUserRelationMapper.insert(wordBookUserRelation);
    }


    @Override
    public List<WordDictionary> matchWords(String words) {
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

    @Override
    public void addWordsToWordBook(Long wordBookId, Long userId, List<Long> wordIds) {
        // 检查单词本是否存在
        WordBook wordBook = wordBookMapper.selectById(wordBookId);
        if (wordBook == null) {
            log.warn("单词本{}不存在", wordBookId);
            throw new BusinessException("单词本不存在");
        }
        // 检查用户是否对单词本有写权限
        QueryWrapper<WordBookUserRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word_book_id", wordBookId).eq("user_id", userId).eq("status", 1);
        WordBookUserRelation wordBookUserRelation = wordBookUserRelationMapper.selectOne(queryWrapper);
        if (wordBookUserRelation == null) {
            log.warn("用户{}对单词本{}没有写权限", userId, wordBookId);
            throw new BusinessException("用户对单词本没有写权限");
        }
        // 找出还没有加入该单词本的单词
        List<Long> needAddWordIds = new ArrayList<>();
        wordIds.forEach(wordId -> {
            QueryWrapper<WordBookDictionaryRelation> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("word_id", wordId);
            WordBookDictionaryRelation wordBookDictionaryRelation = wordBookDictionaryRelationMapper.selectOne(queryWrapper2);
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
    }

}
