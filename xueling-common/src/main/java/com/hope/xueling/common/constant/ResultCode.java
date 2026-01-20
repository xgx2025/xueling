package com.hope.xueling.common.constant;
/**
 * 统一的结果码枚举类
 * @author 谢光湘
 * @date 2026/1/20
 */
public enum ResultCode {
   TEST(111,"测试枚举");

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
