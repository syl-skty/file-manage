package com.skty.plugins.filemanage.config.web;

import com.skty.plugins.filemanage.exception.ExceptionCode;
import com.skty.plugins.filemanage.exception.nonruntime.BaseNonRunTimeException;
import com.skty.plugins.filemanage.exception.runtime.BaseRuntimeException;
import com.skty.plugins.filemanage.exception.runtime.UnHandlerException;
import com.skty.plugins.filemanage.kit.HttpUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理全局异常数据，
 * 根据不同的请求数据，返回对应的数据(json,html,jpg  )
 */
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 异常处理器
     */
    @ExceptionHandler({BaseRuntimeException.class, BaseNonRunTimeException.class, Exception.class})
    public Object handleJson(Exception e, HttpServletRequest request) throws UnHandlerException {
        //输出日志
        e.printStackTrace();
        //如果当前请求需要返回的是json数据，就返回json错误数据回去,否则返回html数据
        if (HttpUtils.requestAcceptType(request, MediaType.APPLICATION_JSON, MediaType.ALL)) {
            if (e instanceof BaseRuntimeException) {
                BaseRuntimeException base = (BaseRuntimeException) e;
                return new ResponseEntity<>(base, base.getCode().httpStatus);
            } else if (e instanceof BaseNonRunTimeException) {
                BaseNonRunTimeException baseNon = (BaseNonRunTimeException) e;
                return new ResponseEntity<>(baseNon, baseNon.getCode().httpStatus);
            } else {
                return new ResponseEntity<>(e.getMessage(), ExceptionCode.INTERNAL_EXCEPTION.httpStatus);
            }
        } else {
            if (e instanceof BaseRuntimeException) {//如果当前抛出的异常是我们自定义异常，则需要按照自定义异常的方式进行返回
                BaseRuntimeException runTimeException = (BaseRuntimeException) e;
                return new ModelAndView("error", runTimeException.getDetails());
            } else if (e instanceof BaseNonRunTimeException) {
                BaseNonRunTimeException nonRunTimeException = (BaseNonRunTimeException) e;
                return new ModelAndView("error", nonRunTimeException.getDetails());
            } else {
                Map<String, String> message = new HashMap<>();
                message.put("reason", e.getMessage());
                return new ModelAndView("error", message);
            }
        }
    }

}
