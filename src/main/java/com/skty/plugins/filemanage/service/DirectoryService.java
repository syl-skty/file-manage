package com.skty.plugins.filemanage.service;

public interface DirectoryService {
    /**
     * 创建一个新的目录
     *
     * @param parentDirId 父目录的id
     * @param name        创建目录的名字
     * @return true/false
     */
    boolean addDir(Long parentDirId, String name);
}
