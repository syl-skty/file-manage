package com.skty.plugins.filemanage.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skty.plugins.filemanage.db.entity.File;
import com.skty.plugins.filemanage.vo.Element;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件实体操作类dao
 */
@Repository
public interface FileMapper extends BaseMapper<File> {
    /**
     * 更新文件名
     */
    @Update("update file set name=#{newName},updateDate=now() where id=#{fileId}")
    int updateFileName(Long fileId, String newName);

    /**
     * 获取指定目录下的文件
     *
     * @param dirId 目录id
     */
    @Select("select id,name,create_date as createDate,update_date as updateDate,file_size as size,path,1 as eleType from file where dir_id=#{dirId}")
    List<Element> getFilesByDir(Long dirId);
}
