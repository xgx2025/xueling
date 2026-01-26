package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.dto.WordDictionaryDTO;

/**
 * 翻译服务接口
 * @author 谢光湘
 * @since 2026/1/26
 */
public interface ITranslationService {
    /**
     *翻译单词
     * @param word 单词
     * @return 翻译结果
     */
    WordDictionaryDTO translateWord(String word);
}
