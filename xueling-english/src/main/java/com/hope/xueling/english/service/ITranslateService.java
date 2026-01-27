package com.hope.xueling.english.service;

/**
 * 翻译服务接口
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
