package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.entity.WordDictionary;

import java.util.List;
/**
 * 单词本服务接口
 * @author 谢光湘
 * @since 2026/1/26
 */
public interface IWordBookService {
    /**
     * 创建一个单词本
     * @param name 单词本名称
     */
    void createWordBook(String name, Long userId);
    /**
     * 匹配用户输入的单词是否参在于系统的单词库中
     * @param words 用户输入的单词列表
     */
    List<WordDictionary> matchWords(String words);

    /**
     * 将用户输入的单词添加到单词本
     * @param wordBookId 单词本ID
     * @param words 单词列表
     */
    void addWordsToWordBook(Long wordBookId, Long userId, List<Long> words);
}
