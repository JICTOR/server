package com.fasoo.logic.vo.search;

public class CommitSearchVO {
    private String commitId;

    public CommitSearchVO(String commitId){
        this.commitId = commitId;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }
}
