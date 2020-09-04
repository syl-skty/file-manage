package com.skty.plugins.filemanage.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author zhaoyun
 * @date 2020/9/4 17:34
 */
@RequestMapping("/test")
@Controller
public class TestController {
    @RequestMapping("/stream")
    public void testStream(HttpServletResponse response) throws IOException {
        FileInputStream in = new FileInputStream("C:\\Users\\zhaoyun\\Desktop\\test");
        byte[] buffer = new byte[1024];
        int read = 0;
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setContentLength(-1);
        final ServletOutputStream outputStream = response.getOutputStream();
        while ((read = in.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
            outputStream.flush();
        }
    }
}
