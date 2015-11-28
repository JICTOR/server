package com.fasoo.logic.git;

import com.fasoo.logic.utils.file.FileUtils;
import com.fasoo.logic.vo.git.GitCommitVO;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class GitFile {

    private Git git;
    private String gitPath;

    public boolean copyGitCommitList(List<GitCommitVO> commitList, Git git, String gitPath) {

        this.git = git;
        this.gitPath = gitPath;

        FileUtils.makeBaseFolder(FileUtils.UPLOADED_FOLDER_PATH);
        FileUtils.makeBaseFolder(FileUtils.UPLOADED_FOLDER_PATH + commitList.get(0).getGitCommitId());

        if (commitList.size() < 2) {
            if (!checkoutCommit(commitList, 0)) {
                return false;
            }
            lastCommit(commitList);
            return true;
        }

        for (int commitIndex = 0; commitIndex < commitList.size() - 1; commitIndex++) {
            if (!checkoutCommit(commitList, commitIndex)) {
                return false;
            }

            for (int diffIndex = 0; diffIndex < commitList.get(commitIndex).getDiffList().size(); diffIndex++) {
                switch (commitList.get(commitIndex).getDiffList().get(diffIndex).getType()) {
                    case ADD:
                    case MODIFY:
                        copyNewPathFile(commitList, commitIndex, diffIndex);
                    default:
                        break;
                }
            }

            FileUtils.makeBaseFolder(FileUtils.UPLOADED_FOLDER_PATH + commitList.get(commitIndex + 1).getGitCommitId());
            for (int diffIndex = 0; diffIndex < commitList.get(commitIndex + 1).getDiffList().size(); diffIndex++) {
                switch (commitList.get(commitIndex + 1).getDiffList().get(diffIndex).getType()) {
                    case DELETE:
                    case MODIFY:
                        copyOldPathFile(commitList, commitIndex + 1, diffIndex);
                        break;
                    default:
                        break;
                }
            }
        }
        return lastCommit(commitList);
    }

    private boolean lastCommit(List<GitCommitVO> commitList) {

        int lastCommitIndex = commitList.size() - 1;
        if (!checkoutCommit(commitList, lastCommitIndex)) {
            return false;
        }

        for (int j = 0; j < commitList.get(lastCommitIndex).getDiffList().size(); j++) {
            switch (commitList.get(lastCommitIndex).getDiffList().get(j).getType()) {
                case ADD:
                case MODIFY:
                    copyNewPathFile(commitList, lastCommitIndex, j);
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private boolean checkoutCommit(List<GitCommitVO> commitList, int lastCommitIndex) {

        try {
            git.checkout().setName(commitList.get(lastCommitIndex).getGitCommitId()).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void copyNewPathFile(List<GitCommitVO> commitList, int commitIndex, int diffIndex) {

        File copyDiffFile = new File(FileUtils.UPLOADED_FOLDER_PATH, commitList.get(commitIndex).getGitCommitId()
                + File.separator + "NEW" + File.separator + commitList.get(commitIndex).getDiffList().get(diffIndex).getNewPath());
        copyDiffFile.getParentFile().mkdirs();
        File originDiffFile = new File(gitPath + File.separator + commitList.get(commitIndex).getDiffList().get(diffIndex).getNewPath());

        if (!copyDiffFile(copyDiffFile, originDiffFile)) {
            commitList.get(commitIndex).getDiffList().get(diffIndex).setNewFile(copyDiffFile);
            return;
        }
        commitList.get(commitIndex).getDiffList().get(diffIndex).setNewFile(copyDiffFile);
    }

    private void copyOldPathFile(List<GitCommitVO> commitList, int commitIndex, int diffIndex) {

        File copyDiffFile = new File(FileUtils.UPLOADED_FOLDER_PATH, commitList.get(commitIndex).getGitCommitId()
                + File.separator + "OLD" + File.separator + commitList.get(commitIndex).getDiffList().get(diffIndex).getOldPath());
        copyDiffFile.getParentFile().mkdirs();
        File originDiffFile = new File(gitPath + File.separator + commitList.get(commitIndex).getDiffList().get(diffIndex).getOldPath());

        if (!copyDiffFile(copyDiffFile, originDiffFile)) {
            commitList.get(commitIndex).getDiffList().get(diffIndex).setOldFile(copyDiffFile);
            return;
        }
        commitList.get(commitIndex).getDiffList().get(diffIndex).setOldFile(copyDiffFile);
    }

    private boolean copyDiffFile(File copyDiffFile, File originDiffFile) {

        try {
            Files.copy(originDiffFile.toPath(), copyDiffFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}



//0 번째 ~ commitList.size() - 2 까지는
//i + 1 번째의 commit Id 폴더를 만들고
//i 번째로 commit Id로 checkout 한 다음
//i 번째의 new File 을 copy 하고, i + 1 번째의 old File 을 copy 한다.

//commitList.size() - 1 번째는
//commitList.size() - 1 번째 commit Id로 checkout 한 다음 new File 만 copy 한다.