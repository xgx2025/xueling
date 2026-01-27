package com.hope.xueling.english.service;

import com.hope.xueling.english.domain.dto.MaterialContentDTO;
import com.hope.xueling.english.domain.entity.BookIntroduction;
import com.hope.xueling.english.domain.entity.Material;
import com.hope.xueling.english.domain.entity.MaterialCategory;

import java.util.List;

/**
 * 阅读服务接口
 * @author 谢光益
 * @since 2026/1/26
 */
public interface ReadService {

    /**
     * 获取阅读材料分类
     * @return 阅读材料分类列表
     */
    List<MaterialCategory> getMaterialCategories();

    /**
     * 根据分类ID获取阅读材料列表
     * @param categoryId 分类ID
     * @return 阅读材料列表
     */
    List<Material> getMaterialsByCategoryId(String categoryId);

    /**
     * 根据书籍ID获取书籍简介
     * @param materialId 书籍ID
     * @return 书籍简介
     */
    BookIntroduction getBookIntroduction(String materialId);

    /**
     * 根据材料ID和章节ID获取材料内容
     * @param materialContentDTO 阅读材料内容数据传输对象
     * @return 材料内容
     */
    MaterialContentDTO getMaterialContent(MaterialContentDTO materialContentDTO);
}
