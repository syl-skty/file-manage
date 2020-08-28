package com.skty.plugins.filemanage.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

/**
 * 文件实体
 */
@TableName("file")
@Data
@NoArgsConstructor
public class File {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String path;
    @TableField("suffix_path")
    private String suffixPath;
    @TableField("`group`")
    private String group;
    @TableField("server_path")
    private String serverPath;
    @TableField("dir_id")
    private Long dirId;
    @TableField("file_size")
    private Float fileSize;
    @TableField("create_date")
    private Date createDate;
    @TableField("update_date")
    private Date updateDate;
    private String type;

    public File(@NonNull String name, @NonNull Long dirId, String serverPath, String group, String suffixPath, Float fileSize, Date createDate, Date updateDate, String type) {
        this.name = name;
        this.dirId = dirId;
        this.suffixPath = suffixPath;
        this.serverPath = serverPath;
        this.group = group;
        this.fileSize = fileSize;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.type = type;
        this.path = serverPath + "/" + group + "/" + suffixPath;
    }
}
