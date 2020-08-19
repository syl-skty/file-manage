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
     * 当前异常的异常码
     */
    @JsonProperty
    protected ExceptionCode code;

    /**
     * 当前异常信息在内部的异常数据，这一部分可以给内部人员进行查看
     */
    protected String innerMsg;

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
        innerMsg = message;
    }

    /**
     * @param message  可以返回给前端的异常信息
     * @param innerMsg 内部人员查看的异常信息
     */
    public BaseRuntimeException(String message, String innerMsg) {
        super(message);
        this.innerMsg = innerMsg;
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

    public String getInnerMsg() {
        return innerMsg;
    }

    public void setInnerMsg(String innerMsg) {
        this.innerMsg = innerMsg;
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
