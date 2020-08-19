package com.skty.plugins.filemanage.kit;

import org.springframework.util.StringUtils;

/**
 * @author zhaoyun
 * @date 2020/8/19 17:22
 */
public class FileUtils {

    /**
     * 默认文件后缀
     */
    private static final String DEFAULT_EXT = "unknown";

    /**
     * 通过文件名获取文件的文件后缀
     *
     * @param fileName 文件名
     * @return 能识别到就返回.
     */
    public static String getFileExt(String fileName) {
        if (!StringUtils.isEmpty(fileName)) {
            int index = fileName.lastIndexOf(".");
            if (fileName.length() > index + 1) {
                return fileName.substring(index + 1);
            }
        }
        return DEFAULT_EXT;
    }
}
