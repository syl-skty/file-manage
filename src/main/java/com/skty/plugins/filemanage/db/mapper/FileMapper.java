package com.skty.plugins.filemanage.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skty.plugins.filemanage.db.entity.File;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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
}
