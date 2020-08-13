package com.skty.plugins.filemanage.kit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口相应数据
 */
public class ResponseMsg<T> extends HttpEntity<T> implements Serializable {

    /**
     * Create a new, empty {@code HttpEntity}.
     */
    public ResponseMsg() {
    }

    /**
     * Create a new {@code HttpEntity} with the given body and no headers.
     *
     * @param body the entity body
     */
    public ResponseMsg(T body) {
        super(body);
    }

    /**
     * Create a new {@code HttpEntity} with the given headers and no body.
     *
     * @param headers the entity headers
     */
    public ResponseMsg(MultiValueMap<String, String> headers) {
        super(headers);
    }

    /**
     * Create a new {@code HttpEntity} with the given body and headers.
     *
     * @param body    the entity body
     * @param headers the entity headers
     */
    public ResponseMsg(T body, MultiValueMap<String, String> headers) {
        super(body, headers);
    }

    /**
     * 返回一个成功的相应信息
     *
     * @param body
     * @param <T>
     * @return
     */
    public static <T> ResponseMsg<T> SUCCESS(T body) {
        return new ResponseMsg<T>(body, statusHeader(HttpStatus.OK));
    }

    /**
     * @param httpStatus 状态码
     * @return
     */
    private static HttpHeaders statusHeader(HttpStatus httpStatus) {
        HttpHeaders header = new HttpHeaders();
        header.put("status", Collections.singletonList(String.valueOf(httpStatus.value())));
        return header;
    }

}
