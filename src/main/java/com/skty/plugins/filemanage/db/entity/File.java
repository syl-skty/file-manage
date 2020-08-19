package com.skty.plugins.filemanage.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文件实体
 */
@TableName("file")
@Data
public class File {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField("dir_id")
    private Long dirId;
    @TableField("file_size")
    private Float fileSize;
    @TableField("create_date")
    private Date createDate;
    @TableField("update_date")
    private Date updateDate;
    private String type;
}
