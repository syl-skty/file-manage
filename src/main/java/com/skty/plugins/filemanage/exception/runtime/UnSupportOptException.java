package com.skty.plugins.filemanage.exception.runtime;

import com.skty.plugins.filemanage.exception.ExceptionCode;

/**
 * @author zhaoyun
 * @date 2020/8/19 14:31
 */
public class UnSupportOptException extends BaseUnStackTraceException {
    /**
     * 子类需要重写这个方法，设置对应的异常码
     */
    @Override
    protected void initCode() {
        code = ExceptionCode.DEFAULT_EXCEPTION;
    }

    public UnSupportOptException(String message) {
        super(message);
    }


    /**
     * @param message  可以返回给前端的异常信息
     * @param innerMsg 内部人员查看的异常信息
     */
    public UnSupportOptException(String message, String innerMsg) {
        super(message, innerMsg);
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
