package com.fasoo.logic.vo.search;

public class ClassInfoSearchVO {
    private String key;
    private String fqn;

    public ClassInfoSearchVO(String key, String fqn) {
        this.key = key;
        this.fqn = fqn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }
}
