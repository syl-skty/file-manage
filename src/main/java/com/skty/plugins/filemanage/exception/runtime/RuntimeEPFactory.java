package com.skty.plugins.filemanage.exception.runtime;

import java.util.Map;

/**
 * 系统中所有运行时异常仓库
 */
public class RuntimeEPFactory {

    /**
     * 创建一个参数不合法异常
     *
     * @param reason 原因
     */
    public static ParamInvalidException paramInvalidException(String reason) {
        return new ParamInvalidException(reason);
    }

    /**
     * 创建一个参数不合法异常
     *
     * @param reason 原因数据
     */
    public static ParamInvalidException paramInvalidException(Map<String, Object> reason) {
        return new ParamInvalidException(reason);
    }

    /**
     * 使用之前的异常包装当前的异常
     *
     * @param e 无法处理的源异常数据
     */
    public static UnHandlerException unHandlerException(Exception e) {
        return new UnHandlerException(e);
    }

}
