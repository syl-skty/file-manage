package com.skty.plugins.filemanage.controller;

import com.skty.plugins.filemanage.kit.Response;
import com.skty.plugins.filemanage.service.DirectoryService;
import com.skty.plugins.filemanage.vo.DirectoryElementsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/directory")
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    @ResponseBody
    @PostMapping("/parent/{parentDir}/newDir/{dirName}")
    public Object createDir(@PathVariable Long parentDir, @PathVariable String dirName) {
        directoryService.addDir(parentDir, dirName);
        return Response.SUCCESS();
    }

    /**
     * 显示指定目录下的所有元素
     */
    @GetMapping("/{dirId}/elements")
    public ModelAndView showDirElements(@PathVariable Long dirId) {
        ModelAndView mv = new ModelAndView("directory");
        DirectoryElementsVo childElement = directoryService.getChildElementVo(dirId);
        mv.addObject("elements", childElement);
        return mv;
    }

}
