package com.skty.plugins.filemanage;

import com.skty.plugins.filemanage.fdfs.DownloadFDfsFile;
import com.skty.plugins.filemanage.fdfs.FDfsFile;
import com.skty.plugins.filemanage.fdfs.FastDfsTemplate;
import com.skty.plugins.filemanage.fdfs.UploadFDfsFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
@WebAppConfiguration
class FileManageApplicationTests {

    @Autowired
    FastDfsTemplate fastDfsTemplate;

    @Test
    void testDownload() {
        try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\skty\\Desktop\\test2.pdf")) {
            fastDfsTemplate.downloadFile(
                    new DownloadFDfsFile.callbackFile("group1", "M00/00/00/rBEAB18z9N6AO2H2AR5CI56Bqq4353.pdf", downloading -> {
                        try {
                            outputStream.write(downloading.getData());
                            return 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return -1;
                        }
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testUpload() {
        try {
            FDfsFile file = fastDfsTemplate.uploadFile(new UploadFDfsFile.LocalFile("C:\\Users\\zhaoyun\\Desktop\\test.pdf", "pdf", null));
            System.out.println(file.getFileName());
            System.out.println(file.getGroup());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpload1() {
        try {
            FDfsFile file = fastDfsTemplate.uploadFile(new UploadFDfsFile.StreamFile("group1", new FileInputStream("C:\\Users\\skty\\Desktop\\test.pdf"), "pdf"));
            System.out.println(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
