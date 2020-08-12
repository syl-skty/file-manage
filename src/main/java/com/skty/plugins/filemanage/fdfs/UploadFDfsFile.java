package com.skty.plugins.filemanage.fdfs;

import java.io.InputStream;
import java.util.Map;

/**
 * 用于文件上传的文件实体
 */
public class UploadFDfsFile {

    private UploadFDfsFile() {

    }

    /**
     * 使用本地文件进行上传
     */
    public static class LocalFile extends FDfsFile {
        /**
         * 本地文件路径
         */
        private String filePath;

        /**
         * 创建一个需要上传的本地文件
         *
         * @param filePath 文件路径
         * @param fileExt  文件拓展名
         * @param meta     文件属性数据
         */
        public LocalFile(String filePath, String fileExt, Map<String, Object> meta) {
            this.filePath = filePath;
            this.fileExt = fileExt;
            this.meta = meta;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }

    /**
     * 使用文件输入流进行文件上传
     */
    public static class StreamFile extends FDfsFile {
        /**
         * 文件输入流
         */
        private InputStream inputStream;

        /**
         * 指定要写出的数据字节的大小
         */
        private long dataSize;

        /**
         * 传输数据时的偏移量
         */
        private long offset;

        public InputStream getInputStream() {
            return inputStream;
        }

        public StreamFile(String group, String filExt, InputStream inputStream, long dataSize, long offset) {
            this.fileExt = filExt;
            this.inputStream = inputStream;
            this.dataSize = dataSize;
            this.offset = offset;
            this.group = group;
        }

        /**
         * 将整个输入流中的数据全部传输过去
         *
         * @param inputStream 输入流
         * @param filExt      文件扩展名
         */
        public StreamFile(String group, InputStream inputStream, String filExt) {
            this.fileExt = filExt;
            this.group = group;
            this.inputStream = inputStream;
        }


        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            this.offset = 0;
        }

        public long getDataSize() {
            return dataSize;
        }

        public void setDataSize(long dataSize) {
            this.dataSize = dataSize;
        }

        public long getOffset() {
            return offset;
        }

        public void setOffset(long offset) {
            this.offset = offset;
        }
    }

}
