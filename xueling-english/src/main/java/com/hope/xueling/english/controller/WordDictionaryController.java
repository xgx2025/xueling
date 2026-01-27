package com.hope.xueling.english.controller;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.util.ThreadLocalUtils;
import com.hope.xueling.english.domain.vo.WordDictionaryVO;
import com.hope.xueling.english.service.IWordDictionaryService;
import io.jsonwebtoken.Claims;
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
     * @return WordDictionaryVO 翻译结果
     */
    @GetMapping
    public Result<WordDictionaryVO> translateWord(@RequestParam("word") String word) {
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        WordDictionaryVO wordDictionary = wordDictionaryService.queryWordDictionary(word, userId);
        log.info("查询结果: {}", wordDictionary);
        return Result.success(wordDictionary);
    }
}
