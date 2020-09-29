package com.skty.plugins.filemanage.test;

import com.skty.plugins.filemanage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhaoyun
 * @date 2020/9/7 18:41
 */
@Component
@TestAnnotation
public class TestClass {
    @Autowired
    private FileService fileService;

    @TestMethodAnnotation(cron = "24 * * * * ?")
    public void testMy(String name){
        System.out.println(name);
    }
}
