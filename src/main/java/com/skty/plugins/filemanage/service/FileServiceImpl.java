package com.skty.plugins.filemanage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.skty.plugins.filemanage.db.entity.File;
import com.skty.plugins.filemanage.db.mapper.FileMapper;
import com.skty.plugins.filemanage.kit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaoyun
 * @date 2020/8/18 15:37
 */
@Service
public class FileServiceImpl implements FileService {

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
}
