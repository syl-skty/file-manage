package com.skty.plugins.filemanage.vo;

import com.skty.plugins.filemanage.db.entity.Directory;
import com.skty.plugins.filemanage.db.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 文件夹下元素列表数据
 *
 * @author zhaoyun
 * @date 2020/8/19 16:43
 */
@Data
@AllArgsConstructor
public class DirectoryElementsVo {
    /**
     * 当前目录下的所有子文件夹
     */
    private List<Directory> directories;

    /**
     * 文件夹下面的所有文件
     */
    private List<File> fileList;

}
