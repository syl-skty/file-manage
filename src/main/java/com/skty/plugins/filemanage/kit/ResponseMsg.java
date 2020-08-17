package com.skty.plugins.filemanage.kit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.Collections;

/**
 * 接口相应数据
 */
public class ResponseMsg<T> extends ResponseEntity<T> implements Serializable {


    public ResponseMsg(HttpStatus status) {
        super(status);
    }

    public ResponseMsg(T body, HttpStatus status) {
        super(body, status);
    }

    public ResponseMsg(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseMsg(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
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
