package com.skty.plugins.filemanage.exception.runtime;

import java.util.Map;

/**
 * 不打印堆栈信息的异常
 *
 * @author zhaoyun
 * @date 2020/8/19 16:18
 */
public abstract class BaseUnStackTraceException extends BaseRuntimeException {

    /**
     * 不打印当前的堆栈数据
     *
     * @return
     */
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public BaseUnStackTraceException() {
    }

    public BaseUnStackTraceException(String message) {
        super(message);
    }

    /**
     * @param message  可以返回给前端的异常信息
     * @param innerMsg 内部人员查看的异常信息
     */
    public BaseUnStackTraceException(String message, String innerMsg) {
        super(message, innerMsg);
    }

    public BaseUnStackTraceException(Map<String, Object> reasonMap) {
        super(reasonMap);
    }

    public BaseUnStackTraceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseUnStackTraceException(Throwable cause) {
        super(cause);
    }

    public BaseUnStackTraceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
