package com.xu.myWechat.common.bean;

/**
 * Enum 响应类型枚举
 *
 * @date 16/11/01
 * @auther hua xu
 */
public enum ExceptionType {

    SUCCESS("00","success"),
    USER_ERROR("500001","用户错误"),
    ADD_ERROR("500007","添加失败");

    private String code;
    private String message;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
