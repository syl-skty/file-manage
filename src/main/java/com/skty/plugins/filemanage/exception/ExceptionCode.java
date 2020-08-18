package com.skty.plugins.filemanage.exception;

import org.springframework.http.HttpStatus;

/**
 * 当前需要返回的异常码枚举，每个异常类都需要有一个异常码，用于返回当前的异常信息
 */
public enum ExceptionCode {
    COMMON_EXCEPTION(-1, "系统组件异常", HttpStatus.INTERNAL_SERVER_ERROR),
    DEFAULT_EXCEPTION(0, "系统默认异常", HttpStatus.INTERNAL_SERVER_ERROR),
    PARAM_INVALID(400, "当前传入的参数校验不通过", HttpStatus.BAD_REQUEST),
    INTERNAL_EXCEPTION(500, "出现内部异常，但是未知", HttpStatus.INTERNAL_SERVER_ERROR);
    /**
     * 异常码
     */
    public Integer code;

    /**
     * 异常描述信息
     */
    public String description;

    /**
     * 对应的http响应码
     */
    public HttpStatus httpStatus;

    ExceptionCode(Integer code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
