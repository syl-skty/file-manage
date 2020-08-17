package com.skty.plugins.filemanage.config.web;

import com.skty.plugins.filemanage.exception.BaseNonRunTimeException;
import com.skty.plugins.filemanage.exception.BaseRuntimeException;
import com.skty.plugins.filemanage.exception.ExceptionCode;
import com.skty.plugins.filemanage.kit.HttpUtils;
import com.skty.plugins.filemanage.kit.ResponseMsg;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理全局异常数据
 */
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 异常处理器，最先进行处理,只针对json类型返回数据的请求
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseMsg<?> handleJson(Exception e, HttpServletRequest request) throws Exception {
        //如果当前请求需要返回的是json数据，就返回json错误数据回去
        if (HttpUtils.requestAcceptType(request, MediaType.APPLICATION_JSON, MediaType.ALL)) {
            if (e instanceof BaseRuntimeException) {//如果当前抛出的异常是我们自定义异常，则需要按照自定义异常的方式进行返回
                BaseRuntimeException runTimeException = (BaseRuntimeException) e;
                return new ResponseMsg<>(runTimeException, runTimeException.getCode().httpStatus);
            } else if (e instanceof BaseNonRunTimeException) {
                BaseNonRunTimeException nonRunTimeException = (BaseNonRunTimeException) e;
                return new ResponseMsg<>(nonRunTimeException, nonRunTimeException.getCode().httpStatus);
            } else {
                return new ResponseMsg<>(e.getMessage(), ExceptionCode.INTERNAL_EXCEPTION.httpStatus);
            }
        } else {//处理不了需要将其抛给上面带视图的异常处理器
            throw e;
        }
    }

    /**
     * 处理异常处理器
     */
    @Order(10)
    @ExceptionHandler(Exception.class)
    public ModelAndView handlerHtml(Exception e, HttpServletRequest request) throws Exception {
        if (HttpUtils.requestAcceptType(request, MediaType.ALL, MediaType.TEXT_HTML)) {
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
        } else {//这里处理不了，继续上抛，交给其他处理器
            throw e;
        }
    }

    /**
     * 最终异常处理器，当所有的异常处理器都无法处理时，将会触发这个处理器
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(Exception.class)
    public ModelAndView handlerAll(Exception e) {
        Map<String, String> message = new HashMap<>();
        message.put("reason", e.getMessage());
        return new ModelAndView("error", message);
    }

}
