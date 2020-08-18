package com.skty.plugins.filemanage.exception.runtime;

import com.skty.plugins.filemanage.exception.ExceptionCode;

/**
 * 系统无法处理的异常
 *
 * @author zhaoyun
 * @date 2020/8/18 14:04
 */
public class UnHandlerException extends BaseRuntimeException {

    private Exception targetException;

    /**
     * 子类需要重写这个方法，设置对应的异常码
     */
    @Override
    protected void initCode() {
        this.code = ExceptionCode.DEFAULT_EXCEPTION;
    }

    /**
     * 将之前的异常信息进行包装
     *
     * @param targetException 之前的异常
     */
    public UnHandlerException(Exception targetException) {
        this.targetException = targetException;
    }

    public Exception getTargetException() {
        return targetException;
    }

    public void setTargetException(Exception targetException) {
        this.targetException = targetException;
    }

    /**
     * <h1>方法可以为空不写<h1/>
     * <p>
     * 每个异常类都需要将自己的注册到异常工厂，防止后面写代码是乱出现异常，将当前异常类注册到异常工厂中
     * <p>
     * <p>
     * 这个方法只是让你在写代码的时候记得去工厂添加对应的异常初始化方法
     */
    @Override
    protected void registerToFactory() {

    }
}
