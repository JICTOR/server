package com.fasoo.web.controller;

import com.fasoo.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VisualController {

    @Autowired
    private VisualService visualService;

    @RequestMapping(value = "/visual", method = RequestMethod.GET)
    public String displayVisual() {
        return "d3_second_example";
    }

    @RequestMapping(value = "/searchDataList/{userKey}")
    public @ResponseBody String getSearchDataListJson(@PathVariable(value = "userKey") String userKey) {
        return visualService.getSearchDataListJson(userKey);
    }

    @RequestMapping(value = "/searchCommitIdList/{userKey}")
    public @ResponseBody String getSearchCommitIdListJson(@PathVariable(value = "userKey") String userKey) {
        return visualService.getSearchCommitIdListJson(userKey);
    }

    @RequestMapping(value = "/commitIdList/{userKey}")
    public @ResponseBody String getCommitIdListJson(@PathVariable(value = "userKey") String userKey) {
        return visualService.getCommitIdListJson(userKey);
    }

    @RequestMapping(value = "/fullyQualifiedNameList/git/{commitId}")
    public @ResponseBody String getGitFullyQualifiedNameListJson(@PathVariable(value = "commitId") String commitId) {
        return visualService.fullyQualifiedNameListJsonFromCommitId(commitId);
    }

    @RequestMapping(value = "/fullyQualifiedNameList/classInfo/{userKey}")
    public @ResponseBody String getFullyQualifiedNameListJson(@PathVariable(value = "userKey") String userKey) {
        return visualService.getFullyQualifiedNameListJson(userKey);
    }
}
