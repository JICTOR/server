package com.fasoo.web.controller;

import com.fasoo.service.SunburstVisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SunburstVisualController {

    @Autowired
    private SunburstVisualService sunburstVisualService;

    @RequestMapping(value = "/sunburst/classInfo/{userKey}")
    public @ResponseBody String getSunburstJson(@PathVariable(value = "userKey") String userKey) {
            return sunburstVisualService.classInfoListToSunburstJson(userKey);
    }

    @RequestMapping(value = "/sunburst/git/{commitId}")
    public @ResponseBody String getGitSunburstJson(@PathVariable(value = "commitId") String commitId) {
        return sunburstVisualService.sunburstJsonFromCommitId(commitId);
    }
}
