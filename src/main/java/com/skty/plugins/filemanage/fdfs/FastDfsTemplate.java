package com.skty.plugins.filemanage.fdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * 操作fastDfs的工具类
 */
@Configuration
public class FastDfsTemplate {

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private TrackerClient trackerClient;

    @Value("${fastdfs.bufferSize}")
    private int bufferSize;

    /**
     * 初始化StorageClient
     */
    @Bean
    StorageClient initStorageClient(TrackerClient trackerClient) throws IOException {
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return new StorageClient(trackerServer);
    }

    /**
     * 初始化TrackerClient
     */
    @Bean
    TrackerClient initTrackerClient(@Value("${fastdfs.configpath}") String configPath) throws IOException, MyException {
        Resource resource;
        String classpathUrlPrefix = ResourceUtils.CLASSPATH_URL_PREFIX;
        //如果当前文件路径是使用classpath，则使用classPathResource
        if (configPath.startsWith(classpathUrlPrefix)) {
            resource = new ClassPathResource(configPath.substring(classpathUrlPrefix.length()));
        } else {
            resource = new FileSystemResource(configPath);
        }
        Properties properties;
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException("当前输入的fastdfs配置文件不存在", e);
        }
        ClientGlobal.initByProperties(properties);
        return new TrackerClient();
    }


    /**
     * 上传文件
     *
     * @param localFile 要上传的本地文件
     * @return 上传文件后的文件数据
     */
    public FDfsFile uploadFile(UploadFDfsFile.LocalFile localFile) throws IOException {
        String filePath = localFile.getFilePath();
        try {
            String[] response = storageClient.upload_file(filePath, localFile.getFileExt(), transToPair(localFile.getMeta()));
            return transToFDfsFile(response);
        } catch (IOException | MyException e) {
            throw new IOException("本地文件上传失败", e);
        }
    }

    /**
     * 将一个输入流发送给fastDfs服务器进行文件保存
     *
     * @param streamFile 流文件
     * @return 上传后得到的文件数据
     */
    public FDfsFile uploadFile(UploadFDfsFile.StreamFile streamFile) throws IOException {

        try (InputStream inputStream = streamFile.getInputStream()) {
            if (inputStream == null) {
                throw new IllegalArgumentException("上传文件是，输入流不能为空");
            }
            int available = inputStream.available();
            if (available > 0) {
                //存在偏移量，需要将数据指针进行移动
                long offset = streamFile.getOffset();
                if (offset > 0) {
                    long skip = inputStream.skip(offset);
                    if (skip < offset) {
                        throw new IllegalArgumentException("数据流在进行偏移量移动后，到达流尾部，没有数据进行上传");
                    }
                }
                //指定要输出的字节的个数
                long dataSize = streamFile.getDataSize();

                String[] response = storageClient.upload_file(streamFile.getGroup(), available,
                        outputStream -> {
                            try {
                                byte[] buffer = new byte[bufferSize];
                                long remainSize = dataSize;
                                if (remainSize != 0) {
                                    for (int read; remainSize > 0L; remainSize -= read) {
                                        read = inputStream.read(buffer, 0, remainSize > bufferSize ? bufferSize : (int) remainSize);
                                        if (read < 0) {
                                            break;
                                        }
                                    }
                                } else {
                                    for (int read; (read = inputStream.read(buffer, 0, bufferSize)) > 0; ) {
                                        outputStream.write(buffer, 0, read);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                return -1;
                            }
                            return 0;
                        }, streamFile.getFileExt(), transToPair(streamFile.getMeta()));

                return transToFDfsFile(response);
            } else {
                throw new IllegalArgumentException("数据流中不存在数据，没有数据进行上传");
            }
        } catch (IOException | MyException e) {
            throw new IOException("文件上传失败", e);
        }
    }

    /**
     * 回到函数式下载文件
     *
     * @param callbackFile 流读取时的回调函数
     * @return true，文件正常下载完成,false，下载失败
     */
    public boolean downloadFile(DownloadFDfsFile.callbackFile callbackFile) throws IOException {
        try {
            boolean status = false;
            Consumer<Downloading> callback = callbackFile.getCallback();
            if (callback != null) {
                status = storageClient.download_file(callbackFile.getGroup(), callbackFile.getFileName(), (l, bytes, i) -> {
                    if (callbackFile.getDownloadByteNum() == -1) {
                        callbackFile.setDownloadByteNum(l);
                        callbackFile.setFileOffset(0L);
                    }
                    if (i > 0) {
                        Downloading downloading = new Downloading(l, Arrays.copyOf(bytes, i));
                        callback.accept(downloading);
                    }
                    return 0;
                }) == 0;
            }
            return status;
        } catch (IOException | MyException e) {
            throw new IOException("文件下载失败.", e);
        }
    }


    /**
     * 转换
     *
     * @param meta 文件属性
     * @return 转换好的文件属性对
     */
    private NameValuePair[] transToPair(Map<String, Object> meta) {
        NameValuePair[] nameValuePairs = null;
        if (!CollectionUtils.isEmpty(meta)) {
            int size = meta.size();
            nameValuePairs = new NameValuePair[size];
            int i = 0;
            for (Map.Entry<String, Object> entry : meta.entrySet()) {
                nameValuePairs[i] = new NameValuePair(entry.getKey(), Objects.toString(entry.getValue(), ""));
                i++;
            }
        }
        return nameValuePairs;
    }


    /**
     * 将响应体进行转换为文件体
     *
     * @param response 响应体
     * @return 文件体
     */
    private FDfsFile transToFDfsFile(String[] response) {
        if (response != null && response.length > 1) {
            return new FDfsFile(response[1], response[0]);
        } else {
            throw new IllegalArgumentException("文件上传失败，返回数据错误，数据为:" + Arrays.asList(Objects.requireNonNull(response)));
        }
    }

}
