package com.skty.plugins.filemanage.config.web;

import com.skty.plugins.filemanage.exception.ExceptionCode;
import com.skty.plugins.filemanage.exception.nonruntime.BaseNonRunTimeException;
import com.skty.plugins.filemanage.exception.runtime.BaseRuntimeException;
import com.skty.plugins.filemanage.exception.runtime.RuntimeEPFactory;
import com.skty.plugins.filemanage.exception.runtime.UnHandlerException;
import com.skty.plugins.filemanage.kit.HttpUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * 异常处理器，最先进行处理,只针对json类型返回数据的请求，如果处理不了，就会抛处理不了的异常，交给专门处理其他请求资源类型的异常处理器
     */
    @Order
    @ResponseBody
    @ExceptionHandler({BaseRuntimeException.class, BaseNonRunTimeException.class, Exception.class})
    public Object handleJson(Exception e, HttpServletRequest request) throws UnHandlerException {
        //如果当前请求需要返回的是json数据，就返回json错误数据回去
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
        } else {//处理不了需要将其抛给上面带视图的异常处理器
            throw RuntimeEPFactory.unHandlerException(e);
        }
    }


    /**
     * 处理异常处理器,最终版的，如果这边还没有找到，就直接返回错误页面了
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(UnHandlerException.class)
    public ModelAndView handlerHtml(UnHandlerException e) {
        Exception targetException = e.getTargetException();
        if (targetException instanceof BaseRuntimeException) {//如果当前抛出的异常是我们自定义异常，则需要按照自定义异常的方式进行返回
            BaseRuntimeException runTimeException = (BaseRuntimeException) targetException;
            return new ModelAndView("error", runTimeException.getDetails());
        } else if (targetException instanceof BaseNonRunTimeException) {
            BaseNonRunTimeException nonRunTimeException = (BaseNonRunTimeException) targetException;
            return new ModelAndView("error", nonRunTimeException.getDetails());
        } else {
            Map<String, String> message = new HashMap<>();
            message.put("reason", e.getMessage());
            return new ModelAndView("error", message);
        }
    }

}
