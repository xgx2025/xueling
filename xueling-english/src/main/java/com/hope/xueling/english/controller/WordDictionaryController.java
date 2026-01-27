package com.hope.xueling.english.controller;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.service.IWordDictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionary")
public class WordDictionaryController {
    private final IWordDictionaryService wordDictionaryService;

    /**
     * 翻译单词
     * @param word 单词
     * @return WordDictionary 翻译结果
     */
    @GetMapping
    public Result<WordDictionary> translateWord(@RequestParam("word") String word) {
        WordDictionary wordDictionary = wordDictionaryService.queryWordDictionary(word);
        log.info("查询结果: {}", wordDictionary);
        return Result.success(wordDictionary);
    }
}
