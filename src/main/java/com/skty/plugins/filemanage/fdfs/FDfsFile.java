package com.skty.plugins.filemanage.fdfs;

import java.util.Map;

/**
 * 抽象出来fDfs文件对象
 */
public class FDfsFile {
    /**
     * 文件名(不包含全路径)
     */
    protected String fileName;
    /**
     * 文件扩展名
     */
    protected String fileExt;

    /**
     * 文件所在的组
     */
    protected String group;

    /**
     * 文件所附带的属性
     */
    protected Map<String, Object> meta;


    public FDfsFile() {
    }

    public FDfsFile(String fileName, String group) {
        this.fileName = fileName;
        this.group = group;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "FDfsFile{" +
                "fileName='" + fileName + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", group='" + group + '\'' +
                ", meta=" + meta +
                '}';
    }
}
