package com.fasoo.service.impl;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.json.JsonUtils;
import com.fasoo.logic.vo.git.GitCommitVO;
import com.fasoo.logic.vo.rank.RankVO;
import com.fasoo.parser.gitTest.GitDiffVO;
import com.fasoo.service.DashBoardService;
import com.mongodb.util.JSON;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;

import java.util.*;

//TODO : 여기 코드 다시 고치기
@Service
public class DashBoardServiceImpl implements DashBoardService{

    @Override
    public String useRankJson(String userKey) {

        Map<String, Set<String>> classUseMap = MongoDBUtils.findClassUseMapFromProjectUserId(userKey);
        DualHashBidiMap fullyQualifiedNameList = MongoDBUtils.findFullyQualifiedNameMapFromProjectUserKey(userKey);

        List<RankVO> rankList = new ArrayList<RankVO>();

        for(String classUseKey : classUseMap.keySet()){
            rankList.add(new RankVO(fullyQualifiedNameList.get(classUseKey).toString(), classUseMap.get(classUseKey).size()));
        }

        Collections.sort(rankList, new Comparator<RankVO>() {
            @Override
            public int compare(RankVO o1, RankVO o2) {
                return o1.getValue() > o2.getValue() ? -1 : o1.getValue() < o2.getValue() ? 1:0;
//                return o1.getValue() < o2.getValue() ? -1 : o1.getValue() > o2.getValue() ? 1:0;
            }
        });

        return JsonUtils.objectToJson(rankList);
    }

    @Override
    public String usedRankJson(String userKey) {
        Map<String, Set<String>> usedClassMap = MongoDBUtils.findUsedClassMapFromProjectUserId(userKey);
        DualHashBidiMap fullyQualifiedNameList = MongoDBUtils.findFullyQualifiedNameMapFromProjectUserKey(userKey);

        List<RankVO> rankList = new ArrayList<RankVO>();

        for(String classUseKey : usedClassMap.keySet()){
            rankList.add(new RankVO(fullyQualifiedNameList.get(classUseKey).toString(), usedClassMap.get(classUseKey).size()));
        }

        Collections.sort(rankList, new Comparator<RankVO>() {
            @Override
            public int compare(RankVO o1, RankVO o2) {
                return o1.getValue() > o2.getValue() ? -1 : o1.getValue() < o2.getValue() ? 1:0;
//                return o1.getValue() < o2.getValue() ? -1 : o1.getValue() > o2.getValue() ? 1:0;
            }
        });

        return JsonUtils.objectToJson(rankList);
    }

    @Override
    public String commitClassCountList(String userKey) {
        List<String> commitIdList = MongoDBUtils.findAllCommitIdFromGitUserId(userKey);
        if(commitIdList == null){
            return null;
        }

        DualHashBidiMap fullyQualifiedNameMap;
        List<RankVO> rankList = new ArrayList<RankVO>();
        for(String commitId : commitIdList){
            fullyQualifiedNameMap = MongoDBUtils.findFullyQualifiedNameMapFromCommitId(commitId);
            if(fullyQualifiedNameMap == null){
                continue;
            }
            rankList.add(new RankVO(commitId, fullyQualifiedNameMap.size()));
        }

        return JsonUtils.objectToJson(rankList);
    }

    @Override
    public String commitDiffCountList(String userKey) {
        List<String> commitIdList = MongoDBUtils.findAllCommitIdFromGitUserId(userKey);
        if(commitIdList == null){
            return null;
        }

        List<GitDiffVO> gitDiffList;
        List<RankVO> rankList = new ArrayList<RankVO>();
        for(String commitId : commitIdList){
            gitDiffList = MongoDBUtils.findDiffListFromCommitId(commitId);
            if(gitDiffList == null){
                continue;
            }
            rankList.add(new RankVO(commitId, gitDiffList.size()));
        }

        return JsonUtils.objectToJson(rankList);
    }

    //TODO : 가장 많이 변한 파일의 순위
//    @Override
//    public String diffFileChangeRank(String userKey) {
//        List<String> commitIdList = MongoDBUtils.findAllCommitIdFromGitUserId(userKey);
//        if(commitIdList == null){
//            return null;
//        }
//
//        List<GitDiffVO> gitDiffList;
//        Map<String, Integer> rankMap = new HashMap<String, Integer>();
//        for(String commitId : commitIdList){
//            gitDiffList = MongoDBUtils.findDiffListFromCommitId(commitId);
//            if(gitDiffList == null){
//                continue;
//            }
//            for(GitDiffVO gitDiff : gitDiffList){
//                switch (gitDiff.getType()){
//                    case ADD:
//                    case MODIFY:
//                        if(rankMap.containsKey(gitDiff.getNewPath())){
//                            rankMap.put(gitDiff.getNewPath(), rankMap.get(gitDiff.getNewPath()) + 1);
//                        }
//                        rankMap.put(gitDiff.getNewPath(), 1);
//                    case DELETE:
//                        if(rankMap.containsKey(gitDiff.getOldPath())){
//                            rankMap.put(gitDiff.getOldPath(), rankMap.get(gitDiff.getOldPath()) + 1);
//                        }
//                        rankMap.put(gitDiff.getOldPath(), 1);
//                }
//            }
//        }
//
//        List<RankVO> rankList= new ArrayList<RankVO>();
//        for(String key : rankMap.keySet()){
//            rankList.add(new RankVO(key, rankMap.get(key)));
//        }
//
//        Collections.sort(rankList, new Comparator<RankVO>() {
//            @Override
//            public int compare(RankVO o1, RankVO o2) {
//                return o1.getValue() > o2.getValue() ? -1 : o1.getValue() < o2.getValue() ? 1:0;
////                return o1.getValue() < o2.getValue() ? -1 : o1.getValue() > o2.getValue() ? 1:0;
//            }
//        });
//
//        return JsonUtils.objectToJson(rankList);
//    }
}
