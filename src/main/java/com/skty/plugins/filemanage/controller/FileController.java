package com.skty.plugins.filemanage.controller;

import com.skty.plugins.filemanage.db.entity.File;
import com.skty.plugins.filemanage.kit.Response;
import com.skty.plugins.filemanage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     *
     * @param file  文件数据
     * @param dirId 目标文件夹id
     */
    @ResponseBody
    @PostMapping("/dir/{dirId}")
    public Object uploadFile(MultipartFile file, @PathVariable Long dirId) {
        File fileByFDFS = fileService.uploadFileByFDFS(file, dirId);
        return Response.SUCCESS().addResult("file", fileByFDFS);
    }


    /**
     * 下载文件
     *
     * @param fileId 文件id
     */
    @GetMapping("/{fileId}")
    public void downloadFile(HttpServletResponse response, @PathVariable Long fileId) {
        fileService.downloadFileByFDFS(response, fileId);
    }

}
