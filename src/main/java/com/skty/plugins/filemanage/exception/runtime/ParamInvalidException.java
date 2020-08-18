package com.skty.plugins.filemanage.exception.runtime;

import com.skty.plugins.filemanage.exception.ExceptionCode;

import java.util.Map;

/**
 * 参数检查异常
 */
public class ParamInvalidException extends BaseRuntimeException {

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

    ParamInvalidException(Map<String, Object> reasonMap) {
        super(reasonMap);
    }

}
