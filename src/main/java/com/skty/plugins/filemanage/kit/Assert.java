package com.skty.plugins.filemanage.kit;

import com.skty.plugins.filemanage.exception.runtime.RuntimeEPFactory;

/**
 * 断言工具类，用于参数判断
 *
 * @author zhaoyun
 * @date 2020/8/18 15:25
 */
public abstract class Assert {
    /**
     * 非空断言
     */
    public static void notNull(Object o, String msg) {
        if (o == null) {
            throw RuntimeEPFactory.paramInvalidException(msg);
        }
    }
}
