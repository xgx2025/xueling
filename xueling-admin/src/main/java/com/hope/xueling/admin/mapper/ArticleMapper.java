package com.hope.xueling.admin.mapper;

import com.hope.xueling.admin.domain.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文章Mapper
 * @author 谢光益
 * @since 2026/1/31
 */
@Repository("adminArticleMapper")
@Mapper
public interface ArticleMapper {

    /**
     * 插入文章
     * @param article 文章
     */
    @Insert("insert into article(title,chinese_title,category_id,tag,author,content,chinese_meaning,image_url,vocabulary_phrases_summary,article_insights,is_free) values(#{title},#{chineseTitle},#{categoryId},#{tag},#{author},#{content},#{chineseMeaning},#{imageUrl},#{vocabularyPhrasesSummary},#{articleInsights},#{isFree})")
    void insertArticle(Article article);
}
