package com.hope.xueling.common.util;

import cn.hutool.core.util.ReUtil;
import org.springframework.stereotype.Component;

/**
 * 字符串工具类，提供字符串处理相关的方法
 * @author 谢光湘
 * @since 2026/1/27
 */
@Component
public class StrUtils {
    /**
     * 提取字符串中的中文字符
     * @param str 输入字符串
     * @return 仅包含中文字符的字符串
     */
    public String extractChinese(String str){
        return String.join("", ReUtil.findAll("[\\u4e00-\\u9fa5]", str, 0));
    }
    /**
     * 提取字符串中的英文字符
     * @param str 输入字符串
     * @return 仅包含英文字符的字符串
     */
    public String extractEnglish(String str){
        return String.join("",ReUtil.findAll("[a-zA-Z]", str, 0));
    }
}
