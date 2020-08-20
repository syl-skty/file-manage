package com.skty.plugins.filemanage.kit;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应给前端的数据体
 *
 * @author zhaoyun
 * @date 2020/8/20 15:16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {


    /**
     * 响应码，-1/1--》失败/成功
     */
    private Integer code;

    private String message;

    public static final Integer SUCCESS = 1;
    public static final Integer FAIL = -1;

    //为空就不必要将值进行返回
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Map<String, Object> result;

    public static final String MESSAGE_SUCCESS = "success";

    public static final String MESSAGE_FAIL = "fail";

    /**
     * 成功的响应
     */
    public static Response SUCCESS() {
        return new Response(SUCCESS, MESSAGE_SUCCESS);
    }


    /**
     * 成功的响应
     *
     * @param result 结果集
     */
    public static Response SUCCESS(Map<String, Object> result) {
        return new Response(SUCCESS, MESSAGE_SUCCESS, result);
    }


    /**
     * 增加返回结果集中的内容
     */
    public Response addResult(String key, Object value) {
        if (result == null) {
            result = new HashMap<>();
        }
        result.put(key, value);
        return this;
    }

    /**
     * 设置返回值
     */
    public Response setResult(Map<String, Object> result) {
        this.result = result;
        return this;
    }

    /**
     * 失败的响应
     */
    public static Response FAIL() {
        return new Response(FAIL, MESSAGE_FAIL);
    }


    private Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(Integer code, String message, Map<String, Object> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResult() {
        return result;
    }
}
