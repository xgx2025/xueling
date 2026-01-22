package com.hope.xueling.common.exception;

import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理类，用于处理应用程序中抛出的异常
 * @author 谢光湘
 * @since 2026/1/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理数据库唯一冲突异常
     * @param e 重复键异常对象
     * @return 错误信息
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e){
        log.error("数据库唯一冲突异常",e);
        String errorMessage = e.getMessage();
        String friendlyMsg = "数据已存在";
        //正则匹配：从异常信息中提取 值、字段名
        Pattern pattern = Pattern.compile("Duplicate entry '(.*?)' for key '(.*?)'");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()){
            String duplicateValue = matcher.group(1);   //冲突的值
            String keyName = matcher.group(2);  //冲突的字段名

            if(keyName.contains("username")){
                log.info("用户名冲突");
                friendlyMsg = String.format("用户名 %s 已被使用", duplicateValue);
            }else if (keyName.contains("email")){
                log.info("邮箱冲突");
                friendlyMsg = String.format("邮箱 %s 已注册", duplicateValue);
            }else if (keyName.contains("phone")){
                log.info("手机号冲突");
                friendlyMsg = String.format("手机号 %s 已绑定账号", duplicateValue);

            }
        }
        return Result.fail(ResultCode.DUPLICATE_KEY_ERROR, friendlyMsg);
    }



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
