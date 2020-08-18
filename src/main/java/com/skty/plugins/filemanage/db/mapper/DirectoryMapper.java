package com.skty.plugins.filemanage.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skty.plugins.filemanage.db.entity.Directory;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 目录表mapper
 */
@Repository
public interface DirectoryMapper extends BaseMapper<Directory> {
    /**
     * 获取当前目录的子目录
     *
     * @param dirId 当前目录id
     */
    @Select("select id,name,parent_id,create_date from directory where dir_id=#{dirId}")
    List<Directory> getChildDir(Long dirId);

    /**
     * 获取当前目录的子目录
     *
     * @param dirId 当前目录id
     */
    @Select("select id from directory where dir_id=#{dirId}")
    List<Long> getChildDirIds(Long dirId);

    @Select("select id,name,parent_id as parentId,create_date as createDate from directory")
    List<Directory> getAllDir();
}
