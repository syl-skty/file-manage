package com.skty.plugins.filemanage.controller;

import com.skty.plugins.filemanage.service.DirectoryService;
import com.skty.plugins.filemanage.vo.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaoyun
 * @date 2020/9/3 15:48
 */
@RestController
@RequestMapping("/data/dir/")
public class DirectoryDataController {
    @Autowired
    private DirectoryService directoryService;

    /**
     * 获取指定目录下的所有元素数据
     */
    @ResponseBody
    @GetMapping("/{dirId}/elements")
    public List<Element> getDirElements(@PathVariable Long dirId) {
        return directoryService.getChildElements(dirId);
    }
}
