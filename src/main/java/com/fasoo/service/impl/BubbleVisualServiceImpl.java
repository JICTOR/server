package com.fasoo.service.impl;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.visual.bubble.BubbleVisual;
import com.fasoo.service.BubbleVisualService;
import org.springframework.stereotype.Service;

@Service
public class BubbleVisualServiceImpl implements BubbleVisualService {

    @Override
    public String useBubbleJson(String userKey, String classInfoKey) {
        BubbleVisual bubbleVisual = new BubbleVisual();
        return bubbleVisual.useClassInfoJson(classInfoKey, MongoDBUtils.findClassUseMapFromProjectUserId(userKey),
                MongoDBUtils.findFullyQualifiedNameMapFromProjectUserKey(userKey));
    }

    @Override
    public String usedBubbleJson(String userKey, String classInfoKey) {
        BubbleVisual bubbleVisual = new BubbleVisual();
        return bubbleVisual.usedClassInfoJson(classInfoKey, MongoDBUtils.findUsedClassMapFromProjectUserId(userKey),
                MongoDBUtils.findFullyQualifiedNameMapFromProjectUserKey(userKey));
    }

    @Override
    public String gitUseBubbleJson(String commitId, String classInfoKey) {
        BubbleVisual bubbleVisual = new BubbleVisual();
        return bubbleVisual.useClassInfoJson(classInfoKey, MongoDBUtils.findClassUseMapFromCommitId(commitId),
                MongoDBUtils.findFullyQualifiedNameMapFromCommitId(commitId));
    }

    @Override
    public String gitUsedBubbleJson(String commitId, String classInfoKey) {
        BubbleVisual bubbleVisual = new BubbleVisual();
        return bubbleVisual.usedClassInfoJson(classInfoKey, MongoDBUtils.findUsedClassMapFromCommitId(commitId),
                MongoDBUtils.findFullyQualifiedNameMapFromCommitId(commitId));
    }
}
