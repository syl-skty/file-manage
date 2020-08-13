package com.skty.plugins.filemanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skty.plugins.filemanage.entity.Directory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 目录表mapper
 */
@Mapper
public interface DirectoryMapper extends BaseMapper<Directory> {
    @Select("select id,name,parent_id,create_date from directory where dir_id=#{dirId}")
    List<Directory> getChildDir(Long dirId);

    @Select("select id,name,parent_id as parentId,create_date as createDate from directory")
    List<Directory> getAllDir();
}
