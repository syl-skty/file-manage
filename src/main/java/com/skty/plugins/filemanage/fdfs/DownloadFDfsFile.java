package com.skty.plugins.filemanage.fdfs;

import java.util.function.Function;

/**
 * 文件下载数据
 */
public class DownloadFDfsFile {

    /**
     * 下载文件到本地包装类
     */
    public static class toLocalFile extends FDfsFile{

    }

    public static class callbackFile extends FDfsFile{
        /**
         * 下载时的回调函数
         */
        private Function<Downloading, Integer> callback;

        /**
         * 下载文件时的字节偏移量
         */
        private long fileOffset;

        /**
         * 要下载的字节数
         */
        private long downloadByteNum = -1;


        public callbackFile(String group, String fileName, long fileOffset, long downloadByteNum, Function<Downloading, Integer> callback) {
            this.fileName = fileName;
            this.group = group;
            this.callback = callback;
            this.fileOffset = fileOffset;
            this.downloadByteNum = downloadByteNum;
        }

        public callbackFile(String group, String fileName, Function<Downloading, Integer> callback) {
            this.fileName = fileName;
            this.group = group;
            this.callback = callback;
        }

        public Function<Downloading, Integer> getCallback() {
            return callback;
        }

        public void setCallback(Function<Downloading, Integer> callback) {
            this.callback = callback;
        }

        public long getFileOffset() {
            return fileOffset;
        }

        public void setFileOffset(long fileOffset) {
            this.fileOffset = fileOffset;
        }

        public long getDownloadByteNum() {
            return downloadByteNum;
        }

        public void setDownloadByteNum(long downloadByteNum) {
            this.downloadByteNum = downloadByteNum;
        }
    }

}
