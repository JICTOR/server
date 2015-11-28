package com.fasoo.logic.git;

import com.fasoo.logic.vo.git.GitCommitVO;
import com.fasoo.parser.gitTest.GitDiffVO;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.InvalidPathException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class GitCommitDiff {

    private Git git;
    private String gitPath;

    public List<GitCommitVO> getGitCommitList(Git git, String gitPath) {

        List<GitCommitVO> result = new ArrayList<GitCommitVO>();
        this.git = git;
        this.gitPath = gitPath;

        LogCommand log = git.log();
        Iterable<RevCommit> logMessages = null;
        try {
            logMessages = log.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return result;
        }

        String newHash = null;
        String oldHash;
        int commitLogCount = 0;
        int stackCount = 0;
        GitCommitVO gitCommit;
        Stack<GitCommitVO> commitStack = new Stack<GitCommitVO>();

        for (RevCommit commit : logMessages) {

            gitCommit = new GitCommitVO();
            oldHash = commit.getId().getName();

            if (commitLogCount > 0) {
                try {
                    gitCommit.setDiffList(getGitDiffList(newHash, oldHash));
                } catch (GitAPIException e) {
                    e.printStackTrace();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return result;
                }
                gitCommit.setGitCommitId(newHash);
                commitStack.push(gitCommit);
                stackCount++;
            }
            commitLogCount++;
            newHash = oldHash;
        }

        gitCommit = getFirstGitCommit(newHash);
        if (gitCommit != null) {
            commitStack.add(gitCommit);
            stackCount++;
        }

        for (int i = 0; i < stackCount; i++) {
            GitCommitVO commitInfo = commitStack.pop();
            if(commitInfo.getDiffList().isEmpty()){
                continue;
            }
            result.add(commitInfo);
        }

        return result;
    }

    private GitCommitVO getFirstGitCommit(String newHash) {

        GitCommitVO gitCommit = new GitCommitVO();
        gitCommit.setGitCommitId(newHash);

        try {
            git.checkout().setName(newHash).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidPathException i){
            i.printStackTrace();
            return null;
        }

        File firstFile = new File(gitPath);
        String gitPathPattern = Pattern.quote(gitPath + File.separator);
        List<GitDiffVO> firstDiffInfoList = new ArrayList<GitDiffVO>();
        getFirstFileList(firstDiffInfoList, firstFile, gitPathPattern);

        gitCommit.setDiffList(firstDiffInfoList);

        return gitCommit;
    }

    private void getFirstFileList(List<GitDiffVO> firstDiffInfoList, File firstFile, String gitPathPattern) {

        for (File file : firstFile.listFiles()) {
            if (file.isDirectory()) {
                getFirstFileList(firstDiffInfoList, file, gitPathPattern);
            } else if (file.getName().endsWith(".java")) {
                GitDiffVO firstDiffInfo;
                firstDiffInfo = new GitDiffVO();
                firstDiffInfo.setType(DiffEntry.ChangeType.ADD);
                firstDiffInfo.setNewPath(file.getAbsolutePath().split(gitPathPattern)[1]);
                firstDiffInfoList.add(firstDiffInfo);
            }
        }
    }


    private List<GitDiffVO> getGitDiffList(String newHash, String oldHash) throws GitAPIException, IOException {

        List<GitDiffVO> result = new ArrayList<GitDiffVO>();

        Repository repository = git.getRepository();
        ObjectId newId = repository.resolve(newHash + "^{tree}");
        ObjectId oldId = repository.resolve(oldHash + "^{tree}");

        ObjectReader reader = repository.newObjectReader();

        CanonicalTreeParser newTree = new CanonicalTreeParser();
        newTree.reset(reader, newId);
        CanonicalTreeParser oldTree = new CanonicalTreeParser();
        oldTree.reset(reader, oldId);

        GitDiffVO diff;
        List<DiffEntry> diffEntryList = git.diff().setNewTree(newTree).setOldTree(oldTree).call();
        for (DiffEntry diffEntry : diffEntryList) {
            if (!(diffEntry.getOldPath().endsWith(".java") || diffEntry.getNewPath().endsWith(".java"))) {
                continue;
            }

            diff = new GitDiffVO();
            switch (diffEntry.getChangeType()) {
                case DELETE:
                    diff.setOldPath(diffEntry.getOldPath());
                    break;
                case MODIFY:
                    diff.setOldPath(diffEntry.getOldPath());
                    diff.setNewPath(diffEntry.getNewPath());
                    break;
                case ADD:
                    diff.setNewPath(diffEntry.getNewPath());
                    break;
                default:
                    continue;
            }

            diff.setType(diffEntry.getChangeType());
            result.add(diff);
        }
        return result;
    }
}