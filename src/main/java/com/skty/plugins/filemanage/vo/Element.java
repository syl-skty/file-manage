package com.skty.plugins.filemanage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 元素实体
 *
 * @author zhaoyun
 * @date 2020/9/3 15:54
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Element {
    /**
     * 元素id
     */
    Long id;
    /**
     * 元素名
     */
    protected String name;
    /**
     * 创建时间
     */
    protected Date createDate;
    /**
     * 上一次更新时间
     */
    protected Date updateDate;
    /**
     * 元素路径，用于打开文件或者文件夹
     */
    protected String path;
    /**
     * 元素大小
     */
    protected Float size;

    /**
     * 元素类型
     */
    protected Integer eleType;
}
