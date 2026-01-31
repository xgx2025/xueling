package com.hope.xueling.admin.controller.english;

import com.hope.xueling.admin.domain.dto.ArticleDTO;
import com.hope.xueling.admin.service.english.impl.ArticleServiceImpl;
import com.hope.xueling.common.domain.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.net.MalformedURLException;

/**
 * 阅读控制器
 * @author 谢光益
 * @since 2026/1/31
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminReadController")
@RequestMapping("/admin/read")
public class ReadController {

    private final ArticleServiceImpl articleService;


    /**
     * 上传文章
     * @param articleDTO 文章信息
     */
    @PostMapping("/article")
    public Result<String> uploadArticle(@RequestBody ArticleDTO articleDTO) throws MalformedURLException {
        log.info("正在上传文章······");
        articleService.uploadArticle(articleDTO);
        log.info("上传文章成功^_^");
        return Result.success("上传成功","上传文章成功");
    }
}
