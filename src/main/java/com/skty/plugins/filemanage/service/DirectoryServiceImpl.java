package com.skty.plugins.filemanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.skty.plugins.filemanage.db.entity.Directory;
import com.skty.plugins.filemanage.db.entity.File;
import com.skty.plugins.filemanage.db.mapper.DirectoryMapper;
import com.skty.plugins.filemanage.db.mapper.FileMapper;
import com.skty.plugins.filemanage.exception.runtime.RuntimeEPFactory;
import com.skty.plugins.filemanage.kit.Assert;
import com.skty.plugins.filemanage.vo.DirectoryElementsVo;
import com.skty.plugins.filemanage.vo.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private FileService fileService;

    @Autowired
    private DirectoryMapper directoryMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 创建一个新的目录
     *
     * @param parentDirId 父目录的id
     * @param name        创建目录的名字
     * @return true/false
     */
    @Override
    public void addDir(Long parentDirId, String name) {
        QueryWrapper<Directory> wrapper = Wrappers.query();
        Integer parentCount = directoryMapper.selectCount(wrapper.eq("id", parentDirId));
        if (parentCount > 0) {//父文件存在，表示可以继续执行
            wrapper.clear();
            Integer existsCount = directoryMapper.selectCount(wrapper.eq("parent_id", parentDirId)
                    .and(objectQueryWrapper -> objectQueryWrapper.eq("name", name)));
            if (existsCount < 1) {
                Directory directory = new Directory();
                directory.setName(name);
                directory.setParentId(parentDirId);
                directory.setCreateDate(new Date());
                directoryMapper.insert(directory);
            } else {
                throw RuntimeEPFactory.paramInvalidException("当前目录已经存在，无法创建目录");
            }
        } else {
            throw RuntimeEPFactory.paramInvalidException("父文件不存在，无法为其创建目录");
        }
    }

    /**
     * 检查当前目录是否存在子元素（在删除目录前需要进行判断）
     *
     * @param dirId 需要判断的目录
     * @return true/false
     */
    @Override
    public boolean checkDirHasChild(Long dirId) {
        return checkDirHasChildDir(dirId) || checkDirHasChildFile(dirId);
    }

    /**
     * 判断当前目录是否存在子目录
     *
     * @param dirId
     */
    @Override
    public boolean checkDirHasChildDir(Long dirId) {
        return getDirChildDirCount(dirId) > 0;
    }

    /**
     * 获取目录下子目录的数量
     *
     * @param dirId
     */
    @Override
    public int getDirChildDirCount(Long dirId) {
        Assert.notNull(dirId, "目录不能为空");
        return directoryMapper.selectCount(Wrappers.<Directory>query().eq("parent_id", dirId));
    }

    /**
     * 判断当前目录是否存在子文件
     *
     * @param dirId
     */
    @Override
    public boolean checkDirHasChildFile(Long dirId) {
        return fileService.getFileCountByDir(dirId) > 0;
    }

    /**
     * 删除当前目录
     *
     * @param dirId   要删除的目录
     * @param recurse 是否递归删除其子元素
     */
    @Override
    public void deleteDir(Long dirId, boolean recurse) {
        Assert.notNull(dirId, "删除的目录不存在");
        if (recurse) {//如果需要进行递归删除，就递归删除数据
            recurseDelete(dirId);
        } else {//如果没有递归，就先查看是否存在子目录，如果不存在，就删除成功，如果存在，就删除失败
            if (checkDirHasChildDir(dirId)) {
                throw RuntimeEPFactory.unSupportOptException("当前删除的目录下存在子目录，无法完成删除，请先将子目录删除，或者执行递归删除");
            }

            //不存在子目录就直接删除当前目录下的所有文件,之后将当前目录进行删除
            fileMapper.delete(Wrappers.<File>query().eq("dir_id", dirId));
            directoryMapper.deleteById(dirId);
        }
    }

    /**
     * 递归删除指定目录下的文件
     *
     * @param dirId 指定目录
     */
    private void recurseDelete(Long dirId) {
        //先删除目录下的直接文件
        fileMapper.delete(Wrappers.<File>query().eq("dir_id", dirId));

        //查询当前目录下的所有子目录
        List<Long> childDirIds = directoryMapper.getChildDirIds(dirId);

        //找到子目录后，将自己删除
        directoryMapper.deleteById(dirId);

        //对所有子目录进行递归操作，删除里面所有的文件
        if (!CollectionUtils.isEmpty(childDirIds)) {
            for (Long id : childDirIds) {
                recurseDelete(id);
            }
        }
    }


    /**
     * 修改目录的名字
     *
     * @param dirId   目录id
     * @param newName 要修改为的新名字
     */
    @Override
    public void modifyDirName(Long dirId, String newName) {
        Assert.notNull(dirId, "操作的目录不能为空");
        Assert.notBlank(newName, "目录修改到的新名字不能为空");
        directoryMapper.updateDirName(dirId, newName);
    }

    /**
     * 获取当前目录下所有元素的数据（子目录/文件）
     *
     * @param dirId 目录id
     * @return
     */
    @Override
    public DirectoryElementsVo getChildElementVo(Long dirId) {
        Assert.notNull(dirId, "目录id不能为空");
        //查出所有目录
        List<Directory> directories = directoryMapper.selectList
                (Wrappers.<Directory>query().eq("parent_id", dirId).orderByAsc("create_date"));

        //所有文件
        List<File> fileList = fileMapper.selectList
                (Wrappers.<File>query().eq("dir_id", dirId).orderByAsc("update_date"));

        return new DirectoryElementsVo(directories, fileList);
    }

    /**
     * 获取当前目录下所有元素的数据（子目录/文件）
     *
     * @param dirId 目录id
     */
    @Override
    public List<Element> getChildElements(Long dirId) {
        Assert.notNull(dirId, "目录id不能为空");
        List<Element> elements = new ArrayList<>(directoryMapper.getChildDirById(dirId));
        elements.addAll(fileMapper.getFilesByDir(dirId));
        return elements;
    }
}
