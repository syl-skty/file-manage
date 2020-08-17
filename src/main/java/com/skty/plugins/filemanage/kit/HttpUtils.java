package com.skty.plugins.filemanage.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 判断当前浏览器请求是否是指定的内容数据,只要有一个匹配上就行。html或者json
     *
     * @return true：包含这种返回类型，false，不包含
     */
    public static boolean requestAcceptType(HttpServletRequest request, MediaType... mediaTypes) {
        try {
            return Optional.ofNullable(request.getHeaders(HttpHeaders.ACCEPT)).map(StringUtils::toStringArray)
                    .map(Arrays::asList).map(MediaType::parseMediaTypes)
                    .filter(types -> types.stream().anyMatch(type -> {
                        return Arrays.asList(mediaTypes).contains(type);
                    })).isPresent();
        } catch (Exception e) {
            logger.info("转换浏请求数据类型失败", e);
            return false;
        }
    }
}
