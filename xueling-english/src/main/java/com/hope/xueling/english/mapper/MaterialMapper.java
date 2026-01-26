package com.hope.xueling.english.mapper;

import com.hope.xueling.english.domain.dto.MaterialContentDTO;
import com.hope.xueling.english.domain.entity.BookIntroduction;
import com.hope.xueling.english.domain.entity.Material;
import com.hope.xueling.english.domain.entity.MaterialCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 阅读材料Mapper
 * @author 谢光益
 * @since 2026/1/26
 */
@Mapper
public interface MaterialMapper {
    /**
     * 获取阅读材料分类列表
     * @return 阅读材料分类列表
     */
    @Select("select distinct id, name from material_category")
    List<MaterialCategory> getMaterialCategories();

    /**
     * 根据分类ID获取阅读材料列表
     * @param categoryId 分类ID
     * @return 阅读材料列表
     */
    @Select("select id, title, type, category_id, tag, author from material where category_id = #{categoryId}")
    List<Material> getMaterialsByCategoryId(String categoryId);

    /**
     * 根据书籍ID获取书籍简介
     * @param materialId 书籍ID
     * @return 书籍简介
     */
    @Select("select introduction, chapter_count from book where material_id = #{materialId}")
    BookIntroduction getBookIntroduction(String materialId);

    /**
     * 根据材料ID和章节ID获取章节内容
     * @param materialContentDTO 阅读材料内容数据传输对象
     * @return 章节内容
     */
     MaterialContentDTO getChapterContent(MaterialContentDTO materialContentDTO);
}
