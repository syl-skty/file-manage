package com.skty.plugins.filemanage.exception.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skty.plugins.filemanage.exception.ExceptionCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础自定义运行时异常抽象类，后面新建的运行时异常类都需要继承当前的类
 */
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "message", "suppressed"})
public abstract class BaseRuntimeException extends RuntimeException {

    /**
     * Fills in the execution stack trace. This method records within this
     * {@code Throwable} object information about the current state of
     * the stack frames for the current thread.
     *
     * <p>If the stack trace of this {@code Throwable} {@linkplain
     * Throwable#Throwable(String, Throwable, boolean, boolean) is not
     * writable}, calling this method has no effect.
     *
     * @return a reference to this {@code Throwable} instance.
     * @see Throwable#printStackTrace()
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    /**
     * 当前异常的异常码
     */
    @JsonProperty
    protected ExceptionCode code;

    /**
     * 异常详情，当异常中有额外的数据异常数据时，可以考虑把一些异常信息放在这里
     */
    @JsonProperty
    protected Map<String, Object> details;

    {
        registerToFactory();
        initCode();
        if (code == null) {
            throw new BaseRuntimeException("当前自定义异常类:{" + getClass().getName() + "}没有自定义异常码，无法创建其实体") {
                @Override
                protected void initCode() {
                    code = ExceptionCode.COMMON_EXCEPTION;
                }

                @Override
                protected void registerToFactory() {

                }
            };
        }
    }

    BaseRuntimeException() {
    }

    BaseRuntimeException(String message) {
        super(message);
        putDetail("reason", message);
    }

    BaseRuntimeException(Map<String, Object> reasonMap) {
        super();
        this.details = reasonMap;
    }

    BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 子类需要重写这个方法，设置对应的异常码
     */
    protected abstract void initCode();


    public ExceptionCode getCode() {
        return code;
    }

    public void setCode(ExceptionCode code) {
        this.code = code;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public void putDetail(String key, String value) {
        if (details == null) {
            details = new HashMap<>();
        }
        details.put(key, value);
    }

    /**
     * <h1>方法可以为空不写<h1/>
     * <p>
     * 每个异常类都需要将自己的注册到异常工厂，防止后面写代码是乱出现异常，将当前异常类注册到异常工厂中
     * <p>
     * <p>
     * 这个方法只是让你在写代码的时候记得去工厂添加对应的异常初始化方法
     */
    protected abstract void registerToFactory();

}
