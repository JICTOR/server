package com.fasoo.service.impl;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.service.SunburstVisualService;
import org.springframework.stereotype.Service;

@Service
public class SunburstVisualServiceImpl implements SunburstVisualService {

    @Override
    public String classInfoListToSunburstJson(String userKey) {
        return MongoDBUtils.findSunburstJsonFromProjectUserId(userKey);
    }

    @Override
    public String sunburstJsonFromCommitId(String commitId) {
        return MongoDBUtils.findSunburstJsonFromCommitId(commitId);
    }
}
