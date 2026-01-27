package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.dto.WordDictionaryDTO;
import com.hope.xueling.english.domain.vo.WordDictionaryVO;

/**
 * 单词字典服务接口
 * @author 谢光湘
 * @since 2026/1/25
 */
public interface IWordDictionaryService {
    /**
     * 查询单词
     * @param word 英语单词
     * @return 单词字典
     */
    WordDictionaryVO queryWordDictionary(String word, Long userId);
    /**
     * 保存单词字典
     * @param wordDictionary 单词字典
     */
    void saveWordDictionary(Long id,WordDictionaryDTO wordDictionary);
}
