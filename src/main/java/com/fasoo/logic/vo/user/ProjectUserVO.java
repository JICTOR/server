package com.fasoo.logic.vo.user;

import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProjectUserVO {
    private String userKey;
    private DualHashBidiMap fullyQualifiedNameMap;
    private String sunburstJson;
    private Map<String, Set<String>> classUseMap;
    private Map<String, Set<String>> usedClassMap;

    public ProjectUserVO() {
        fullyQualifiedNameMap = new DualHashBidiMap();
        classUseMap = new HashMap<String, Set<String>>();
        usedClassMap = new HashMap<String, Set<String>>();
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public DualHashBidiMap getFullyQualifiedNameMap() {
        return fullyQualifiedNameMap;
    }

    public void setFullyQualifiedNameMap(DualHashBidiMap fullyQualifiedNameMap) {
        this.fullyQualifiedNameMap = fullyQualifiedNameMap;
    }

    public String getSunburstJson() {
        return sunburstJson;
    }

    public void setSunburstJson(String sunburstJson) {
        this.sunburstJson = sunburstJson;
    }

    public Map<String, Set<String>> getClassUseMap() {
        return classUseMap;
    }

    public void setClassUseMap(Map<String, Set<String>> classUseMap) {
        this.classUseMap = classUseMap;
    }

    public Map<String, Set<String>> getUsedClassMap() {
        return usedClassMap;
    }

    public void setUsedClassMap(Map<String, Set<String>> usedClassMap) {
        this.usedClassMap = usedClassMap;
    }
}
