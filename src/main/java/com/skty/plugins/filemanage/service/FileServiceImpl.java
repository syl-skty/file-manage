package com.skty.plugins.filemanage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.skty.plugins.filemanage.db.entity.File;
import com.skty.plugins.filemanage.db.mapper.FileMapper;
import com.skty.plugins.filemanage.exception.runtime.RuntimeEPFactory;
import com.skty.plugins.filemanage.fdfs.DownloadFDfsFile;
import com.skty.plugins.filemanage.fdfs.FDfsFile;
import com.skty.plugins.filemanage.fdfs.FastDfsTemplate;
import com.skty.plugins.filemanage.fdfs.UploadFDfsFile;
import com.skty.plugins.filemanage.kit.Assert;
import com.skty.plugins.filemanage.kit.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
        Assert.notNull(newFile.getName(), "文件名不能为空");
        Assert.notNull(newFile.getDirId(), "所在文件夹不能为空");
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
    public File uploadFileByFDFS(MultipartFile file, Long dirId) {
        Assert.notNull(dirId, "上传的文件不能没有指定的文件夹");
        Assert.notNull(file, "上传的文件不能为空");

        try (InputStream inputStream = file.getInputStream()) {
            String name = file.getOriginalFilename();
            //上传之前需要先判断当前文件是不是已经存在了，如果存在，需要返回提示
            Integer count = fileMapper.selectCount(Wrappers.<File>query()
                    .eq("name", name).and(wrapper -> wrapper.eq("dir_id", dirId)));
            if (count > 0) {
                throw RuntimeEPFactory.unSupportOptException("该文件夹下已经存在同名的文件，无法创建文件");
            }
            int available = inputStream.available();
            if (available < 1) {
                throw RuntimeEPFactory.paramInvalidException("上传文件大小不能为空");
            }
            float sizeM = available >> 10;
            String fileExt = FileUtils.getFileExt(name);
            Date now = new Date();
            FDfsFile fDfsFile = fastDfsTemplate.uploadFile(new UploadFDfsFile.StreamFile("group1", inputStream, fileExt));
            //文件上传成功，更新数据库
            if (fDfsFile != null) {
                //文件下载路径
                String pathSuffix = fDfsFile.getFileName();
                String serverPath = fDfsFile.getServerPath();
                String group = fDfsFile.getGroup();
                File f = new File(name, dirId, serverPath, group, pathSuffix, sizeM, now, now, fileExt);
                this.newFile(f);
                return f;
            } else {
                throw RuntimeEPFactory.operationFailException("文件上传失败", "文件上传失败，原因-》上传后返回的文件内容为null");
            }
        } catch (IOException e) {
            throw RuntimeEPFactory.operationFailException(e, "文件上传失败", "文件上传失败，出现了系统IO异常");
        }
    }

    /**
     * 通过fdfs获取文件下载流。之后传输给浏览器
     *
     * @param response http响应
     * @param fileId   要下载的文件id
     */
    @Override
    public void downloadFileByFDFS(HttpServletResponse response, Long fileId) {
        Assert.notNull(fileId, "要下载的文件不能为空");
        File file = fileMapper.selectById(fileId);
        Assert.notNull(file, "要下载的文件不存在");
        String group = file.getGroup();
        String suffixPath = file.getSuffixPath();

        //设置请求头
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (ServletOutputStream out = response.getOutputStream()) {
            fastDfsTemplate.downloadFile(new DownloadFDfsFile.callbackFile(group, suffixPath, downloading -> {
                try {
                    out.write(downloading.getData());
                    return 0;//正常传输，返回0
                } catch (IOException e) {
                    return -1;//出现异常，就停止传输数据，返回停止的状态码
                }
            }));
        } catch (IOException e) {
            throw RuntimeEPFactory.operationFailException(e, "文件下载失败", "文件下载失败，出现了系统IO异常");
        }
    }
}
