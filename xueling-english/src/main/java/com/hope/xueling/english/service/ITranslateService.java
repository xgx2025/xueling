package com.hope.xueling.english.service;


import com.hope.xueling.common.ai.SmartTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 翻译服务
 * @author 谢光益
 * @since 2026/1/26
 */
public interface ITranslateService{
    /**
     * 翻译单词
     * @param word 单词
     * @return 翻译结果
     */
    String translateWord(String word);
}
