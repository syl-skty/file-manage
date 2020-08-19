package com.skty.plugins.filemanage.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文件夹实体
 */
@TableName("directory")
@Data
public class Directory {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(value = "parent_id")
    private Long parentId;
    @TableField(value = "create_date")
    private Date createDate;

}
