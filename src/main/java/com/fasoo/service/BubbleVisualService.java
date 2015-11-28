package com.fasoo.service;

public interface BubbleVisualService {
    public String useBubbleJson(String userKey, String classInfoKey);
    public String usedBubbleJson(String userKey, String classInfoKey);
    public String gitUseBubbleJson(String commitId, String classInfoKey);
    public String gitUsedBubbleJson(String commitId, String classInfoKey);
}
