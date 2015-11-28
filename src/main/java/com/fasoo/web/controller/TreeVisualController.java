package com.fasoo.web.controller;

import com.fasoo.service.TreeVisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TreeVisualController {

    @Autowired
    private TreeVisualService treeVisualService;

    @RequestMapping(value = "/tree/{classInfoKey}")
    public @ResponseBody String getTreeJson(@PathVariable(value = "classInfoKey") String classInfoKey) {
        return treeVisualService.classInfoToTree(classInfoKey);
    }
}
