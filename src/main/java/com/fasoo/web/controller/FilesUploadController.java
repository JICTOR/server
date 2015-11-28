package com.fasoo.web.controller;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.mail.MailUtils;
import com.fasoo.service.FilesUploadingService;
import com.fasoo.web.command.FilesUploadCommand;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FilesUploadController {

    @Autowired
    private FilesUploadingService filesUploadingService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String displayMainForm() {
        MongoDBUtils.connect("test");
        return "file_upload_form";
    }

    @RequestMapping(value = "/project/{userKey}", method = RequestMethod.GET)
    public String displayResultForm() {
        MongoDBUtils.connect("test");
        return "file_upload_result";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public String uploadFiles(@ModelAttribute("filesUploadCommand") FilesUploadCommand filesUploadCommand) {
        String userKey = DigestUtils.sha1Hex(filesUploadCommand.getUserMail());
        if (MailUtils.setMail(filesUploadCommand.getUserMail(), MailUtils.JICTORURL + "/project/" + userKey)) {
            if (filesUploadingService.parsingFileList(filesUploadCommand, userKey)) {
                MailUtils.sendMail();
                MailUtils.sendMaster(filesUploadCommand.getUserMail(), MailUtils.JICTORURL + "/project/" + userKey);
            }
        }
        return "redirect:/project/" + userKey;
    }
}
