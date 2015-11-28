package com.fasoo.web.controller;

import com.fasoo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashBoardController {
    @Autowired
    DashBoardService dashBoardService;

    @RequestMapping(value = "/dashboard/use/rank/{userKey}")
    public @ResponseBody String getDashBoardUseRankJson(@PathVariable(value = "userKey") String userKey) {
        return dashBoardService.useRankJson(userKey);
    }

    @RequestMapping(value = "/dashboard/used/rank/{userKey}")
    public @ResponseBody String getDashBoardUsedRankJson(@PathVariable(value = "userKey") String userKey) {
        return dashBoardService.usedRankJson(userKey);
    }

    @RequestMapping(value = "/dashboard/commit/class/list/{userKey}")
    public @ResponseBody String getCommitClassCountJson(@PathVariable(value = "userKey") String userKey) {
        return dashBoardService.commitClassCountList(userKey);
    }

    @RequestMapping(value = "/dashboard/commit/diff/list/{userKey}")
    public @ResponseBody String getCommitDiffCountJson(@PathVariable(value = "userKey") String userKey) {
        return dashBoardService.commitDiffCountList(userKey);
    }

    @RequestMapping(value = "/dashboard/diff/change/rank/{userKey}")
    public @ResponseBody String getDiffFileChangeRankJson(@PathVariable(value = "userKey") String userKey) {
        return null;
//        return dashBoardService.diffFileChangeRank(userKey);
    }
}

