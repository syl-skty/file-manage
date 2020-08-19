package com.skty.plugins.filemanage.kit;

import com.skty.plugins.filemanage.exception.runtime.RuntimeEPFactory;
import org.springframework.util.StringUtils;

/**
 * 断言工具类，用于参数判断
 *
 * @author zhaoyun
 * @date 2020/8/18 15:25
 */
public abstract class Assert {


    private Assert() {
    }

    /**
     * 非空断言
     */
    public static void notNull(Object o, String msg) {
        if (o == null) {
            throw RuntimeEPFactory.paramInvalidException(msg);
        }
    }

    /**
     * 字符非空断言
     */
    public static void notBlank(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw RuntimeEPFactory.paramInvalidException(msg);
        }
    }
    
}
