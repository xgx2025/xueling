package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.dto.AddWordsToWordBookDTO;
import com.hope.xueling.english.domain.dto.CreateWordBookDTO;
import com.hope.xueling.english.domain.dto.RemoveWordsFromWordBookDTO;
import com.hope.xueling.english.domain.vo.WordBookDetailVO;
import com.hope.xueling.english.domain.vo.WordBookVO;
import com.hope.xueling.english.domain.vo.WordDictionaryVO;


import java.util.List;

public interface IWordBookService {
    /**
     * 创建一个单词本
     * @param createWordBookDTO 单词本DTO
     */
    void createWordBook(CreateWordBookDTO createWordBookDTO, Long userId);

    /**
     * 获取用户所有单词本
     * @return 用户所有单词本列表
     */
    List<WordBookVO> getWordBooks(Long userId);

    WordBookDetailVO getWordBookDetail(Long wordBookId, Long userId);
    /**
     * 匹配用户输入的单词是否参在于系统的单词库中
     * @param words 用户输入的单词列表
     * @return 匹配到的单词列表
     */
    List<WordDictionaryVO> matchWords(String words);

    /**
     * 将用户输入的单词添加到单词本
     * @param addWordsToWordBookDTO 添加单词到单词本DTO
     * @param userId 用户ID
     */
    void addWordsToWordBook(AddWordsToWordBookDTO addWordsToWordBookDTO, Long userId);


    void deleteWordsFromWordBook(RemoveWordsFromWordBookDTO removeWordsFromWordBookDTO, Long userId);
}
