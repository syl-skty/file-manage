package com.skty.plugins.filemanage.controller;

import com.skty.plugins.filemanage.kit.ResponseMsg;
import com.skty.plugins.filemanage.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/directory")
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    @ResponseBody
    @PostMapping("/parent/{parentDir}/newDir/{dirName}")
    public ResponseMsg<String> createDir(@PathVariable Long parentDir, @PathVariable String dirName) {
        boolean result = directoryService.addDir(parentDir, dirName);
        return ResponseMsg.ok("{\"msg\":\"创建成功\"}").;
    }

}
