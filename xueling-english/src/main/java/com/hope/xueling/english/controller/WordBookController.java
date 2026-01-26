package com.hope.xueling.english.controller;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.util.ThreadLocalUtils;
import com.hope.xueling.english.domain.entity.WordDictionary;
import com.hope.xueling.english.service.IWordBookService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wordbooks")
public class WordBookController {
    private final IWordBookService wordBookService;

    /**
     * 创建一个单词本
     * @param name 单词本名称
     */
    @PostMapping
    public Result<String> createWordBook(@RequestParam("name") String name) {
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        wordBookService.createWordBook(name,userId);
        return Result.success("单词本创建成功");
    }

    /**
     * 匹配用户输入的单词是否参在于系统的单词库中
     * @param words 用户输入的单词列表
     */
    @PostMapping("/match")
    public Result<List<WordDictionary>> matchWords(@RequestParam("words") String words) {
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        List<WordDictionary> matchedWords = wordBookService.matchWords(userId, words);
        return Result.success(matchedWords);
    }

    /**
     * 将用户输入的单词添加到单词本
     * @param wordBookId 单词本ID
     * @param wordIds 单词列表
     */
    @PostMapping("/newWords")
    public Result<String> addWordToWordBook(@RequestParam("wordBookId") Long wordBookId,
                                            @RequestParam("words") List<String> wordIds) {
        Claims claims = ThreadLocalUtils.get();
        Long userId = claims.get("userId", Long.class);
        List<Long> wordIdList = wordIds.stream().map(Long::parseLong).toList();
        wordBookService.addWordsToWordBook(wordBookId,userId,wordIdList);
        return Result.success("单词添加成功");
    }

    /**
     * 处理浏览器直接访问的GET请求
     */
    @GetMapping
    public Result<String> testGetWordBooks() {
        return Result.success("这是单词本API的GET测试端点，浏览器直接访问成功！");
    }
}
