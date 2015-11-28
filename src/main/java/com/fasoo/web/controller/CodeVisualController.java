package com.fasoo.web.controller;

import com.fasoo.service.CodeService;
import com.fasoo.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CodeVisualController {

    @Autowired
    private CodeService codeService;

    @RequestMapping(value = "/code_view/{userKey}/{classInfoKey}", method = RequestMethod.GET)
    public String displayCodeView() {
        return "code_view";
    }

    @RequestMapping(value = "/git_code_view/{commitId}/{classInfoKey}", method = RequestMethod.GET)
    public String displayGitCodeView() {
        return "git_code_view";
    }

    @RequestMapping(value = "/code/classInfo/{userKey}/{classInfoKey}")
    public @ResponseBody String getCodeJson(@PathVariable(value = "userKey") String userKey, @PathVariable(value = "classInfoKey") String classInfoKey) {
        return codeService.classInfoToCode(userKey, classInfoKey);
    }

    @RequestMapping(value = "/code/git/{commitId}/{classInfoKey}")
    public @ResponseBody String getGitCodeJson(@PathVariable(value = "commitId") String commitId, @PathVariable(value = "classInfoKey") String classInfoKey) {
        return codeService.gitCodeJsonFromCommitId(commitId, classInfoKey);
    }
}
