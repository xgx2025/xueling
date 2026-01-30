package com.hope.xueling.english.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 测试Mapper
 * @author 谢光益
 * @since 2026/1/30
 */
public interface TestMapper {

    /**
     * 更新阅读测试题
     * @param userId 用户ID
     * @param articleId 文章ID
     * @param readTest 阅读测试题
     */
    @Update("update test set read_test = #{readTest} where user_id = #{userId} and article_id = #{articleId}")
    void updateReadTest(Long userId, String articleId, String readTest);

    /**
     * 查询阅读测试题
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 阅读测试题
     */
    @Select("select read_test from test where user_id = #{userId} and article_id = #{articleId}")
    String selectReadTest(Long userId, String articleId);
}
