package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.entity.WordDictionary;

import java.util.List;

public interface IWordBookService {
    /**
     * 创建一个单词本
     * @param name 单词本名称
     */
    void createWordBook(String name, Long userId);

    List<WordDictionary> matchWords(Long userId, String words);

    /**
     * 将用户输入的单词添加到单词本
     * @param wordBookId 单词本ID
     * @param words 单词列表
     */
    void addWordsToWordBook(Long wordBookId, Long userId, List<Long> words);
}
