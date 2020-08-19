package com.skty.plugins.filemanage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.skty.plugins.filemanage.db.entity.File;
import com.skty.plugins.filemanage.db.mapper.FileMapper;
import com.skty.plugins.filemanage.exception.runtime.ParamInvalidException;
import com.skty.plugins.filemanage.exception.runtime.RuntimeEPFactory;
import com.skty.plugins.filemanage.fdfs.FDfsFile;
import com.skty.plugins.filemanage.fdfs.FastDfsTemplate;
import com.skty.plugins.filemanage.fdfs.UploadFDfsFile;
import com.skty.plugins.filemanage.kit.Assert;
import com.skty.plugins.filemanage.kit.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author zhaoyun
 * @date 2020/8/18 15:37
 */
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private FastDfsTemplate fastDfsTemplate;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 获取指定目录下的文件个数
     *
     * @param dirId 目录id
     */
    @Override
    public int getFileCountByDir(Long dirId) {
        Assert.notNull(dirId, "目录id不能为空");
        return fileMapper.selectCount(Wrappers.<File>query().eq("dir_id", dirId));
    }

    /**
     * 新建文件
     *
     * @param newFile 新文件数据
     */
    @Override
    public void newFile(File newFile) {
        Assert.notNull(newFile, "创建的文件数据不能为空");
        fileMapper.insert(newFile);
    }

    /**
     * 删除文件
     *
     * @param fileId 要删除的文件id
     */
    @Override
    public void delFile(Long fileId) {
        Assert.notNull(fileId, "删除的文件id不能为空");
        fileMapper.deleteById(fileId);
    }

    /**
     * 文件重命名
     *
     * @param fileId  文件id
     * @param newName 新文件名
     */
    @Override
    public void renameFile(Long fileId, String newName) {
        Assert.notNull(fileId, "文件id不能为空");
        Assert.notBlank(newName, "新文件名不能为空");
        fileMapper.updateFileName(fileId, newName);
    }

    /**
     * 将文件上传到HDfs服务器，并保存文件数据到数据库中
     *
     * @param file
     */
    @Override
    public void uploadFileByHDFS(MultipartFile file) {
        Assert.notNull(file, "上传的文件不能为空");

        try (InputStream inputStream = file.getInputStream()) {
            int available = inputStream.available();
            if (available < 1) {
                throw RuntimeEPFactory.paramInvalidException("上传文件大小不能为空");
            }
            String name = file.getName();
            String fileExt = FileUtils.getFileExt(name);
            FDfsFile fDfsFile = fastDfsTemplate.uploadFile(new UploadFDfsFile.StreamFile("group1", inputStream, fileExt));
            if (fDfsFile != null) {
                
            }
        } catch (ParamInvalidException e) {

        } catch (Exception e) {

        }
    }
}
