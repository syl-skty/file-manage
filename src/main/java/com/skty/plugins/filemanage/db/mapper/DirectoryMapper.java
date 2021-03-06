package com.skty.plugins.filemanage.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skty.plugins.filemanage.db.entity.Directory;
import com.skty.plugins.filemanage.vo.Element;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 目录表mapper
 */
//@Repository
public interface DirectoryMapper extends BaseMapper<Directory> {
    /**
     * 获取当前目录的子目录
     *
     * @param dirId 当前目录id
     */
    @Select("select id,name,parent_id,create_date from directory where parent_id=#{dirId}")
    List<Directory> getChildDir(Long dirId);

    /**
     * 获取当前目录的子目录
     *
     * @param dirId 当前目录id
     */
    @Select("select id from directory where parent_id=#{dirId}")
    List<Long> getChildDirIds(Long dirId);

    @Select("select id,name,parent_id as parentId,create_date as createDate from directory")
    List<Directory> getAllDir();

    /**
     * 更新目录名
     */
    @Update("update directory set name=#{newName} where id=#{dirId}")
    int updateDirName(Long dirId, String newName);

    /**
     * 获取指定目录下的目录
     */
    @Select("select id,name,create_date as createDate,2 as eleType from directory where parent_id=#{dirId}")
    List<Element> getChildDirById(Long dirId);

}
