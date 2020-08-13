package com.skty.plugins.filemanage.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skty.plugins.filemanage.db.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文件实体操作类dao
 */
@Repository
public interface FileMapper extends BaseMapper<File> {

}
