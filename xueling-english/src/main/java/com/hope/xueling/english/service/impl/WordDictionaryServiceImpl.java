package com.hope.xueling.english.service.impl;

import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.mapper.WordDictionaryMapper;
import com.hope.xueling.english.service.IWordDictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 保存单词字典
     * @param wordDictionary 单词字典
     */
    public void saveWordDictionary(WordDictionary wordDictionary) {
        wordDictionaryMapper.insert(wordDictionary);
    }
}
