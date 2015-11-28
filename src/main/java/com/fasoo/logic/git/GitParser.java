package com.fasoo.logic.git;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.visual.sunburst.SunburstVisual;
import com.fasoo.logic.vo.user.GitUserVO;
import com.fasoo.logic.vo.git.GitCommitVO;
import com.fasoo.parser.Parsing;
import com.fasoo.parser.data.DataAll;
import com.fasoo.parser.data.DataClassInfo;
import com.fasoo.parser.gitTest.GitDiffVO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO : compile error 가 났을 때 order가 setting 되지 않은 상태에서 mongodb 에 들어가는 문제 해결하기
public class GitParser {


    public void parsingCommitList(List<GitCommitVO> commitList, String userKey) {

        List<String> insertCommitIdList = new ArrayList<String>();

        DataAll nowDataAll;
        nowDataAll = firstCommitParsing(commitList, insertCommitIdList);
        if (nowDataAll == null) {
            return;
        }
        nthCommitParsing(commitList, nowDataAll, insertCommitIdList);
        GitUserVO gitUser = new GitUserVO(userKey, insertCommitIdList);
        MongoDBUtils.insertGitUser(gitUser);

        return;
    }

    private void nthCommitParsing(List<GitCommitVO> commitList, DataAll nowDataAll, List<String> insertCommitIdList) {

        DataAll beforeDataAll;
        SunburstVisual sunburstVisual;
        String sunburstJson;

        for (int i = 1; i < commitList.size(); i++) {

            beforeDataAll = nowDataAll;
            try {
                nowDataAll = Parsing.changeCommitInfo(beforeDataAll, commitList.get(i).getDiffList());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            sunburstVisual = new SunburstVisual();
            sunburstJson = sunburstVisual.classInfoListToSunburstJson(nowDataAll.getClassInfoList(), nowDataAll.getFqnList());

            if (!sunburstVisual.isCompileError()) {
                commitList.get(i).setFullyQualifiedNameMap(nowDataAll.getFqnList());
                commitList.get(i).setAddedFullyQualifiedNameKeyList(nowDataAll.getAddedDiffList());
                commitList.get(i).setSunburstJson(sunburstJson);
                commitList.get(i).setClassUseMap(sunburstVisual.getClassUseMap());
                commitList.get(i).setUsedClassMap(sunburstVisual.getUsedClassMap());
                commitList.get(i).setOrder(i);
                for (String addedFullyQualifiedNameKey : commitList.get(i).getAddedFullyQualifiedNameKeyList()) {
                    MongoDBUtils.insertClassInfo(addedFullyQualifiedNameKey, (DataClassInfo) nowDataAll.getClassInfoList().get(addedFullyQualifiedNameKey));
                }
                MongoDBUtils.insertCommit(commitList.get(i));
                insertCommitIdList.add(commitList.get(i).getGitCommitId());
            }
        }
    }

    private DataAll firstCommitParsing(List<GitCommitVO> commitList, List<String> insertCommitIdList) {

        DataAll nowDataAll;
        List<File> firstFileList = new ArrayList<File>();

        for (GitDiffVO gitDiff : commitList.get(0).getDiffList()) {
            firstFileList.add(gitDiff.getNewFile());
        }

        try {
            nowDataAll = Parsing.parsing(firstFileList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        SunburstVisual sunburstVisual = new SunburstVisual();
        String sunburstJson = sunburstVisual.classInfoListToSunburstJson(nowDataAll.getClassInfoList(), nowDataAll.getFqnList());

        if (!sunburstVisual.isCompileError()) {
            List<String> addedFullyQualifiedNameKeyList = new ArrayList<String>();
            for (Object addedFullyQualifiedNameKey : nowDataAll.getFqnList().keySet()) {
                addedFullyQualifiedNameKeyList.add(addedFullyQualifiedNameKey.toString());
                MongoDBUtils.insertClassInfo(addedFullyQualifiedNameKey.toString(), (DataClassInfo) nowDataAll.getClassInfoList().get(addedFullyQualifiedNameKey));
            }
            commitList.get(0).setAddedFullyQualifiedNameKeyList(addedFullyQualifiedNameKeyList);
            commitList.get(0).setFullyQualifiedNameMap(nowDataAll.getFqnList());
            commitList.get(0).setSunburstJson(sunburstJson);
            commitList.get(0).setClassUseMap(sunburstVisual.getClassUseMap());
            commitList.get(0).setUsedClassMap(sunburstVisual.getUsedClassMap());
            commitList.get(0).setOrder(0);
            MongoDBUtils.insertCommit(commitList.get(0));
            insertCommitIdList.add(commitList.get(0).getGitCommitId());
        }
        return nowDataAll;
    }
}
