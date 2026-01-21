package com.hope.xueling.common.exception;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类，用于处理应用程序中抛出的异常
 * @author 谢光湘
 * @since 2026/1/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理验证异常
     * @param e 验证异常对象
     * @return 错误信息
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Result<String> handleValidationException(ValidationException e) {
        log.error("验证异常: {}", e.getMessage());
        return Result.fail(ResultCode.VALIDATION_ERROR, e.getMessage());
    }

    /**
     * 处理业务异常
     * @param e 业务异常对象
     * @return 错误信息
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<String> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.fail(ResultCode.BUSINESS_ERROR, e.getMessage());
    }

    /**
     * 处理系统异常
     * @param e 系统异常对象
     * @return 错误信息
     */
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public Result<String> handleSystemException(SystemException e) {
        log.error("系统异常: {}", e.getMessage());
        return Result.fail(ResultCode.SYSTEM_ERROR, e.getMessage());
    }



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
