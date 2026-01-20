package com.hope.xueling.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类，用于处理应用程序中抛出的异常
 * @author 谢光湘
 * @date 2026/1/20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有未捕获的异常
     * @param ex 抛出的异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception ex) {
        // 在这里可以添加日志记录等操作
        return "发生错误: " + ex.getMessage();
    }
}
