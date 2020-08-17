package com.skty.plugins.filemanage.exception;

/**
 * 参数检查异常
 */
public class ParamInvalidException extends BaseRuntimeException {
    /**
     * 当前参数检查时的一些参数异常信息
     */
    private String reason;

    @Override
    protected void initCode() {
        this.code = ExceptionCode.PARAM_INVALID;
    }

    @Override
    protected void registerToFactory() {
        
    }

    ParamInvalidException(String reason) {
        this.reason = reason;
        this.details.put("reason", reason);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
        this.details.put("reason", reason);
    }
}
