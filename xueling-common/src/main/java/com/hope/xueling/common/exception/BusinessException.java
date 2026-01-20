package com.hope.xueling.common.exception;

/**
 * 业务异常类，用于表示业务级别的异常
 * @author 谢光湘
 * @date 2026/1/20
 */
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message);
    }
}
