package com.fasoo.service.impl;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.json.JsonUtils;
import com.fasoo.logic.vo.search.ClassInfoSearchVO;
import com.fasoo.logic.vo.search.CommitSearchVO;
import com.fasoo.service.VisualService;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisualServiceImpl implements VisualService {

    @Override
    public String getFullyQualifiedNameListJson(String userKey) {
        return JsonUtils.objectToJson(MongoDBUtils.findFullyQualifiedNameListFromProjectUserKey(userKey));
    }

    @Override
    public String getCommitIdListJson(String userKey) {
        return JsonUtils.objectToJson(MongoDBUtils.findAllCommitIdFromGitUserId(userKey));
    }

    @Override
    public String fullyQualifiedNameListJsonFromCommitId(String commitId) {
        return JsonUtils.objectToJson(MongoDBUtils.findFullyQualifiedNameListFromCommitId(commitId));
    }

    @Override
    public String getSearchDataListJson(String userKey) {

        DualHashBidiMap fullyQualifiedNameList = MongoDBUtils.findFullyQualifiedNameMapFromProjectUserKey(userKey);
        List<ClassInfoSearchVO> classInfoSearchList = new ArrayList<ClassInfoSearchVO>();
        ClassInfoSearchVO classInfoSearch;
        for (Object fullyQualifiedNameKey : fullyQualifiedNameList.keySet()) {
            classInfoSearch = new ClassInfoSearchVO(fullyQualifiedNameKey.toString(), fullyQualifiedNameList.get(fullyQualifiedNameKey).toString());
            classInfoSearchList.add(classInfoSearch);
        }

        Map map = new HashMap();
        map.put("searchData", classInfoSearchList);

        return JsonUtils.objectToJson(map);
    }

    @Override
    public String getSearchCommitIdListJson(String userKey) {
        List<CommitSearchVO> commitSearchList = new ArrayList<CommitSearchVO>();
        CommitSearchVO commitSearch;
        for(String commitId : MongoDBUtils.findAllCommitIdFromGitUserId(userKey)){
            commitSearch = new CommitSearchVO(commitId);
            commitSearchList.add(commitSearch);
        }

        Map map = new HashMap();
        map.put("searchData", commitSearchList);
        return JsonUtils.objectToJson(map);
    }
}
