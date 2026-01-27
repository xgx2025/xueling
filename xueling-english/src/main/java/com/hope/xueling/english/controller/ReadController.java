package com.hope.xueling.english.controller;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.english.domain.dto.MaterialContentDTO;
import com.hope.xueling.english.domain.entity.BookIntroduction;
import com.hope.xueling.english.domain.entity.Material;
import com.hope.xueling.english.domain.entity.MaterialCategory;
import com.hope.xueling.english.service.impl.ReadServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 阅读控制器
 * @author 谢光益
 * @since 2026/1/26
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/read")
public class ReadController {

    //注入服务实现类
    private final ReadServiceImpl readService;

    /**
     * 获取阅读材料分类列表
     * @return 阅读材料分类列表
     */
    @GetMapping("/categories")
    public Result<List<MaterialCategory>> getReadCategories() {
        log.info("获取阅读材料分类列表");
        List<MaterialCategory> materialCategories = readService.getMaterialCategories();
        return Result.success(materialCategories, "获取阅读材料分类列表成功");
    }

    /**
     * 根据分类ID获取阅读材料列表
     * @param categoryId 分类ID
     * @return 阅读材料列表
     */
    @GetMapping("/materials")
    public Result<List<Material>> getReadMaterialsByCategoryId(@RequestParam("categoryId") String categoryId) {
        log.info("根据分类ID获取阅读材料列表，分类ID：{}", categoryId);
        List<Material> materials = readService.getMaterialsByCategoryId(categoryId);
        return Result.success(materials, "根据分类ID获取阅读材料列表成功");
    }

    /**
     * 根据书籍ID获取书籍简介,含章节数
     * @param materialId 书籍ID
     * @return 书籍简介
     */
    @GetMapping("/introduction")
    public Result<BookIntroduction> getBookIntroduction(@RequestParam("materialId") String materialId) {
        log.info("根据书籍ID获取书籍简介，书籍ID：{}", materialId);
        BookIntroduction bookIntroduction = readService.getBookIntroduction(materialId);
        return Result.success(bookIntroduction, "根据书籍ID获取书籍简介成功");
    }

    /**
     * 根据材料ID和章节ID获取材料内容
     * @param materialContentDTO 阅读材料内容数据传输对象
     * @return 材料内容
     */
    @PostMapping("/chapter")
    public Result<MaterialContentDTO> getChapterContent(@RequestBody MaterialContentDTO materialContentDTO) {
        log.info("根据材料ID和章节ID获取章节内容，材料ID：{}，章节ID：{}", materialContentDTO.getMaterialId(), materialContentDTO.getChapterId());
        MaterialContentDTO materialContent = readService.getMaterialContent(materialContentDTO);
        return Result.success(materialContent, "根据材料ID和章节ID获取材料内容成功");
    }

    //TODO 待完善
}
