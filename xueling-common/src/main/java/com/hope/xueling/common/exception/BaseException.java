package com.hope.xueling.common.exception;

/**
 * 基础异常类，所有自定义异常都应继承自该类
 * @author 谢光湘
 * @date 2026/1/20
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
