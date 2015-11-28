package com.fasoo.web.controller;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.mail.MailUtils;
import com.fasoo.service.GitService;
import com.fasoo.web.command.GitCommand;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//TODO : 프로그램이 종료 될 때 MongoDBUtils.disconnect() 해줘야 함.
@Controller
public class GitController {

    @Autowired
    private GitService gitService;

    @RequestMapping(value = "/git/{userKey}", method = RequestMethod.GET)
    public String displayGitResultForm(@PathVariable(value = "userKey") String userKey) {
        MongoDBUtils.connect("test");
        return "git_upload";
    }

    //TODO : private Git 에 email 은 나중에 적용
//    @RequestMapping(value = "/privateGit", method = RequestMethod.POST)
//    public String privateGitUpload(@ModelAttribute("GitCommand") GitCommand gitCommand) {
//        MongoDBUtils.connect("test");
//        MongoDBUtils.initCollection();
//        gitService.parsingPrivateGitRepository(gitCommand.getGitPath());
//        return "redirect:/git/result";
//    }

    @RequestMapping(value = "/publicGit", method = RequestMethod.POST)
    public String publicGitUpload(@ModelAttribute("GitCommand") GitCommand gitCommand) {
        String userKey = DigestUtils.sha1Hex(gitCommand.getUserMail());
        if (MailUtils.setMail(gitCommand.getUserMail(), MailUtils.JICTORURL + "/git/" + userKey)) {
            if(gitService.parsingPublicGitRepository(gitCommand.getGitPath(), userKey)){
                MailUtils.sendMail();
                MailUtils.sendMaster(gitCommand.getUserMail(), MailUtils.JICTORURL + "/git/" + userKey);
            }
        }
        return "redirect:/git/" + userKey;
    }
}
