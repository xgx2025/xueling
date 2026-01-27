package com.hope.xueling.english.service.impl;

import com.hope.xueling.common.exception.ValidationException;
import com.hope.xueling.english.domain.dto.MaterialContentDTO;
import com.hope.xueling.english.domain.entity.BookIntroduction;
import com.hope.xueling.english.domain.entity.Material;
import com.hope.xueling.english.domain.entity.MaterialCategory;
import com.hope.xueling.english.mapper.MaterialMapper;
import com.hope.xueling.english.service.ReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 阅读服务实现类
 * @author 谢光益
 * @since 2026/1/26
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadServiceImpl implements ReadService {

    private final MaterialMapper materialMapper;

    @Override
    public List<MaterialCategory> getMaterialCategories() {
        return materialMapper.getMaterialCategories();
    }

    @Override
    public List<Material> getMaterialsByCategoryId(String categoryId) {
        //检查分类ID是否为空
        if (categoryId == null || categoryId.isEmpty()) {
            throw new ValidationException("分类 ID 不能为空");
        }
        List<Material> materials = materialMapper.getMaterialsByCategoryId(categoryId);
        return materials;
    }

    @Override
    public BookIntroduction getBookIntroduction(String materialId) {
        //检查材料ID是否为空
        if (materialId == null || materialId.isEmpty()) {
            throw new ValidationException("材料 ID 不能为空");
        }
        //根据材料ID查询书籍简介
        BookIntroduction bookIntroduction = materialMapper.getBookIntroduction(materialId);
        return bookIntroduction;
    }

    @Override
    public MaterialContentDTO getMaterialContent(MaterialContentDTO materialContentDTO) {
        log.info("根据章节ID获取章节内容，章节ID：{}", materialContentDTO.getChapterId());
        MaterialContentDTO materialContent = materialMapper.getChapterContent(materialContentDTO);
        return materialContent;
    }
}
