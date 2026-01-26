package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.domain.vo.WordBookVo;

import java.util.List;

public interface IWordBookService {
    /**
     * 创建一个单词本
     * @param name 单词本名称
     */
    void createWordBook(String name, Long userId);

    /**
     * 获取用户所有单词本
     * @return 用户所有单词本列表
     */
    List<WordBookVo> getWordBooks(Long userId);

    /**
     * 匹配用户输入的单词是否参在于系统的单词库中
     * @param words 用户输入的单词列表
     * @return 匹配到的单词列表
     */
    List<WordDictionary> matchWords(String words);

    /**
     * 将用户输入的单词添加到单词本
     * @param wordBookId 单词本ID
     * @param words 单词列表
     */
    void addWordsToWordBook(Long wordBookId, Long userId, List<Long> words);

}
