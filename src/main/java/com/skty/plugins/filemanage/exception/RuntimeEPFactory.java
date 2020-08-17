package com.skty.plugins.filemanage.exception;

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
}
