package com.hope.xueling.common.exception.biz;

import com.hope.xueling.common.exception.BaseException;

/**
 * 业务异常类，用于表示业务逻辑中的异常情况
 * @author 谢光湘
 * @date 2026/1/20
 */
public class BizException extends BaseException {
    public BizException(String message) {
        super(message);
    }
}
