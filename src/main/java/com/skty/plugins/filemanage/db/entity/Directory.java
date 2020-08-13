package com.skty.plugins.filemanage.db.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * 文件夹实体
 */
@TableName("directory")
public class Directory {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(value = "parent_id")
    private Long parentId;
    @TableField(value = "create_date")
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
