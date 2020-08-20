package com.skty.plugins.filemanage.service;

import com.skty.plugins.filemanage.db.entity.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 新建文件
     *
     * @param newFile 新文件数据
     */
    void newFile(File newFile);

    /**
     * 删除文件
     *
     * @param fileId 要删除的文件id
     */
    void delFile(Long fileId);

    /**
     * 文件重命名
     *
     * @param fileId  文件id
     * @param newName 新文件名
     */
    void renameFile(Long fileId, String newName);

    /**
     * 将文件上传到HDfs服务器，并保存文件数据到数据库中
     */
    File uploadFileByFDFS(MultipartFile file, Long dirId);


    /**
     * 通过fdfs获取文件下载流。之后传输给浏览器
     *
     * @param response 响应流
     * @param fileId   要下载的文件id
     */
    void downloadFileByFDFS(HttpServletResponse response, Long fileId);

}
