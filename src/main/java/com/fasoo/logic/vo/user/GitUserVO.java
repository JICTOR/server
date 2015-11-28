package com.fasoo.logic.vo.user;

import java.util.ArrayList;
import java.util.List;

public class GitUserVO {
    private String userKey;
    private List<String> commitList;

    public GitUserVO(String userKey, List<String> commitList) {
        this.userKey = userKey;
        this.commitList = commitList;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public List<String> getCommitList() {
        return commitList;
    }

    public void setCommitList(List<String> commitList) {
        this.commitList = commitList;
    }
}
