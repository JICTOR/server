package com.fasoo.service.impl;

import com.fasoo.logic.git.GitCommitDiff;
import com.fasoo.logic.git.GitFile;
import com.fasoo.logic.git.GitParser;
import com.fasoo.logic.utils.file.FileUtils;
import com.fasoo.logic.utils.time.TimePrint;
import com.fasoo.logic.vo.git.GitCommitVO;
import com.fasoo.service.GitService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

//TODO : ProgressBar
//TODO : branch 이름 받아서 하는 것 구현
//TODO : branch 리스트 구하는거 구현
@Service
public class GitServiceImpl implements GitService {

//    @Override
//    public void parsingPrivateGitRepository(String path) {
//
//        File gitFolderDir = new File(path);
//        if (gitFolderDir.isDirectory()) {
//            try {
//                git = Git.open(gitFolderDir);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//            gitPath = path;
//            parsingGitRepository();
//        }
//    }

    @Override
    public boolean parsingPublicGitRepository(String gitUrl, String userKey) {

        FileUtils.makeBaseFolder(FileUtils.UPLOADED_FOLDER_PATH);
        File directory = new File(FileUtils.UPLOADED_FOLDER_PATH, userKey);
        FileUtils.deleteDirectory(directory);

        Git git;
        String gitPath;
        try {
            git = Git.cloneRepository().setURI(gitUrl).setDirectory(directory).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }

        gitPath = directory.getAbsolutePath();
        return parsingGitRepository(git, gitPath, userKey);
    }

    private boolean parsingGitRepository(Git git, String gitPath, String userKey) {

        try {
            git.checkout().setName("master").call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            git.close();
            return false;
        }

        GitCommitDiff gitCommitDiff = new GitCommitDiff();
        List<GitCommitVO> commitList = gitCommitDiff.getGitCommitList(git, gitPath);
        if (!commitList.isEmpty()) {
            GitFile gitFile = new GitFile();
            if(gitFile.copyGitCommitList(commitList, git, gitPath)){
                GitParser gitParser = new GitParser();
                gitParser.parsingCommitList(commitList, userKey);
            }
        }

        try {
            git.checkout().setName("master").call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            git.getRepository().close();
            git.close();
            return false;
        }
        git.getRepository().close();
        git.close();
        return true;
    }
}
