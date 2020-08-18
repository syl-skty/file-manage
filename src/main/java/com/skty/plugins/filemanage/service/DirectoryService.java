package com.skty.plugins.filemanage.service;

public interface DirectoryService {
    /**
     * 创建一个新的目录
     *
     * @param parentDirId 父目录的id
     * @param name        创建目录的名字
     * @return true/false
     */
    void addDir(Long parentDirId, String name);


    /**
     * 检查当前目录是否存在子元素（在删除目录前需要进行判断）
     *
     * @param dirId 需要判断的目录
     * @return true/false
     */
    boolean checkDirHasChild(Long dirId);

    /**
     * 判断当前目录是否存在子目录
     */
    boolean checkDirHasChildDir(Long dirId);

    /**
     * 获取目录下子目录的数量
     */
    int getDirChildDirCount(Long dirId);

    /**
     * 判断当前目录是否存在子文件
     */
    boolean checkDirHasChildFile(Long dirId);

    /**
     * 删除当前目录
     *
     * @param dirId   要删除的目录
     * @param recurse 是否递归删除其子元素
     */
    void deleteDir(Long dirId, boolean recurse);

    /**
     * 修改目录的名字
     *
     * @param dirId   目录id
     * @param newName 要修改为的新名字
     */
    void modifyDirName(Long dirId, String newName);
}
