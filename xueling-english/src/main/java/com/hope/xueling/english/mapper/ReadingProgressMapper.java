package com.hope.xueling.english.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 阅读进度Mapper
 * @author 谢光益
 * @since 2026/1/31
 */
public interface ReadingProgressMapper {
    /**
     * 检查用户是否阅读完文章
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 是否阅读完文章
     */
    @Select("select id from article_reading_progress where user_id = #{userId} and article_id = #{articleId} and progress_status = 2")
    Long selectReadStatus(Long userId, Long articleId);

    /**
     * 更新阅读时间
     * @param userId 用户ID
     * @param articleId 文章ID
     * @param readTime 阅读时间
     */
    @Update("update article_reading_progress set read_time = read_time + #{readTime} where user_id = #{userId} and article_id = #{articleId}")
    void updateReadTime(Long userId, Long articleId, Integer readTime);

    /**
     * 检查用户是否阅读时间是否超过3分钟
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 是否超过3分钟
     */
    @Select("select read_time from article_reading_progress where user_id = #{userId} and article_id = #{articleId}")
    Integer selectReadTime(Long userId, Long articleId);

    /**
     * 更新阅读进度为已阅读
     * @param userId 用户ID
     * @param articleId 文章ID
     */
    @Update("update article_reading_progress set is_read = 1 where user_id = #{userId} and article_id = #{articleId}")
    void updateReadStatus(Long userId, Long articleId);
}
