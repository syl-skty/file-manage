package com.skty.plugins.filemanage.fdfs;

public class Downloading {
    /**
     * 文件字节总大小
     */
    private long fileSize;
    /**
     * 此次读写时读取的字节数组
     */
    private byte[] data;

    public Downloading(long fileSize, byte[] data) {
        this.fileSize = fileSize;
        this.data = data;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
