package com.skty.plugins.filemanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.skty.plugins.filemanage.db.entity.Directory;
import com.skty.plugins.filemanage.db.mapper.DirectoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private DirectoryMapper directoryMapper;

    /**
     * 创建一个新的目录
     *
     * @param parentDirId 父目录的id
     * @param name        创建目录的名字
     * @return true/false
     */
    @Override
    public boolean addDir(Long parentDirId, String name) {
        QueryWrapper<Directory> wrapper = Wrappers.query();
        Integer parentCount = directoryMapper.selectCount(wrapper.eq("id", parentDirId));
        if (parentCount > 0) {//父文件存在，表示可以继续执行
            wrapper.clear();
            Integer existsCount = directoryMapper.selectCount(wrapper.eq("parent_id", parentDirId)
                    .and(objectQueryWrapper -> objectQueryWrapper.eq("name", name)));
            if (existsCount == 0) {
                Directory directory = new Directory();
                directory.setName(name);
                directory.setParentId(parentDirId);
                directory.setCreateDate(new Date());
                directoryMapper.insert(directory);
                return true;
            } else {
                throw new IllegalArgumentException("当前目录已经存在，无法创建目录");
            }
        } else {
            throw new IllegalArgumentException("父文件不存在，无法为其创建目录");
        }
    }
}
