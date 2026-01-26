package com.hope.xueling.english.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hope.xueling.english.domain.entity.WordDictionary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 单词字典Mapper接口
 * @author 谢光湘
 * @since 2025/1/25
 */
@Mapper
public interface WordDictionaryMapper extends BaseMapper<WordDictionary> {
}
