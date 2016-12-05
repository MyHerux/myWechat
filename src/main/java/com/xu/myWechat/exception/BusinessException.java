
package com.xu.myWechat.exception;


import com.xu.myWechat.common.bean.ExceptionType;

/**
 * 业务异常
 *
 * @author xuhua
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException(ExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
