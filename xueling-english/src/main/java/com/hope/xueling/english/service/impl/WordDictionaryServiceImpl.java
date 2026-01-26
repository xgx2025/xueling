package com.hope.xueling.english.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hope.xueling.english.domain.dto.WordDictionaryDTO;
import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.mapper.WordDictionaryMapper;
import com.hope.xueling.english.service.ITranslationService;
import com.hope.xueling.english.service.IWordDictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
/**
 * 单词字典服务实现类
 * @author 谢光湘
 * @since 2025/1/25
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WordDictionaryServiceImpl implements IWordDictionaryService {
    private final WordDictionaryMapper wordDictionaryMapper;
    private final ITranslationService translationService;

    @Override
    public WordDictionary queryWordDictionary(String word) {
        QueryWrapper<WordDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word", word);
        WordDictionary entity = wordDictionaryMapper.selectOne(queryWrapper);
        if (entity != null) {
            return entity;
        }
        // 从翻译服务查询翻译
        WordDictionaryDTO translation = translationService.translateWord(word);
        if (translation == null || translation.getWord().trim().isEmpty() || translation.getMeaning().trim().isEmpty()) {
            log.warn("翻译服务未返回翻译结果，单词：{}", word);
            return null;
        }
        // 保存到数据库
        Long id = IdUtil.getSnowflakeNextId();
        saveWordDictionary(id,translation);
        WordDictionary result = new WordDictionary();
        result.setId(id);
        BeanUtils.copyProperties(translation, result);
        return result;
    }

    /**
     * 保存单词字典
     * @param wordDictionary 单词字典
     */
    public void saveWordDictionary(Long id,WordDictionaryDTO wordDictionary) {
        // 转换为实体类
        WordDictionary entity = new WordDictionary();
        entity.setId(id);
        BeanUtils.copyProperties(wordDictionary, entity);
        wordDictionaryMapper.insert(entity);
    }
}
