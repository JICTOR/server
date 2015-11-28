package com.fasoo.service;

public interface DashBoardService {
    public String useRankJson(String userKey);
    public String usedRankJson(String userKey);
    public String commitClassCountList(String userKey);
    public String commitDiffCountList(String userKey);
//    public String diffFileChangeRank(String userKey);
}
