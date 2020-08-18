package com.skty.plugins.filemanage.service;

/**
 * @author zhaoyun
 * @date 2020/8/18 15:36
 */
public interface FileService {
    /**
     * 获取指定目录下的文件个数
     *
     * @param dirId 目录id
     */
    int getFileCountByDir(Long dirId);
}
