package com.fasoo.web.controller;

import com.fasoo.service.BubbleVisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BubbleVisualController {

    @Autowired
    BubbleVisualService bubbleVisualService;

    @RequestMapping(value = "/bubble/use/classInfo/{userKey}/{classInfoKey}")
    public @ResponseBody String getUseBubbleJson(@PathVariable(value = "userKey") String userKey, @PathVariable(value = "classInfoKey") String classInfoKey) {
        return bubbleVisualService.useBubbleJson(userKey, classInfoKey);
    }


    @RequestMapping(value = "/bubble/used/classInfo/{userKey}/{classInfoKey}")
    public @ResponseBody String getUsedBubbleJson(@PathVariable(value = "userKey") String userKey, @PathVariable(value = "classInfoKey") String classInfoKey) {
        return bubbleVisualService.usedBubbleJson(userKey, classInfoKey);
    }

    @RequestMapping(value = "/bubble/use/git/{commitId}/{classInfoKey}")
    public @ResponseBody String getGitUseBubbleJson(@PathVariable(value = "commitId") String commitId, @PathVariable(value = "classInfoKey") String classInfoKey) {
        return bubbleVisualService.gitUseBubbleJson(commitId, classInfoKey);
    }

    @RequestMapping(value = "/bubble/used/git/{commitId}/{classInfoKey}")
    public @ResponseBody String getGitUsedBubbleJson(@PathVariable(value = "commitId") String commitId, @PathVariable(value = "classInfoKey") String classInfoKey) {
        return bubbleVisualService.gitUsedBubbleJson(commitId, classInfoKey);
    }
}
