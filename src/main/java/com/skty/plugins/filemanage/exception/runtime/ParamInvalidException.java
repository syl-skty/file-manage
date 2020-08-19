package com.skty.plugins.filemanage.exception.runtime;

import com.skty.plugins.filemanage.exception.ExceptionCode;

import java.util.Map;

/**
 * 参数检查异常
 */
public class ParamInvalidException extends BaseUnStackTraceException {

    @Override
    protected void initCode() {
        this.code = ExceptionCode.PARAM_INVALID;
    }

    @Override
    protected void registerToFactory() {

    }

    ParamInvalidException(String reason) {
        super(reason);
    }

    /**
     * @param message  可以返回给前端的异常信息
     * @param innerMsg 内部人员查看的异常信息
     */
    public ParamInvalidException(String message, String innerMsg) {
        super(message, innerMsg);
    }

    ParamInvalidException(Map<String, Object> reasonMap) {
        super(reasonMap);
    }

}
