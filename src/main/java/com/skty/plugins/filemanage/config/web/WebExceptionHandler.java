package com.skty.plugins.filemanage.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
public class WebExceptionHandler {

    /**
     * 异常处理器
     */
    @ExceptionHandler(Exception.class)
    public Object handle() {
        return null;
    }
}
