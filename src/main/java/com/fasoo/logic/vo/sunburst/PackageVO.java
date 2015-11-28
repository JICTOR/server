package com.fasoo.logic.vo.sunburst;

import java.util.ArrayList;
import java.util.List;

public class PackageVO {

    private String name;
    private List<PackageVO> children;
    private int lineCount;
    private String classInfoKey;

    private int classUseCount;
    private int usedClassCount;

    public PackageVO() {
        this.children = new ArrayList<PackageVO>();
    }

    public PackageVO(String name) {
        this.name = name;
        this.children = new ArrayList<PackageVO>();
    }

    public void initNameAndChildren(String name) {
        this.name = name;
        this.children = new ArrayList<PackageVO>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PackageVO> getChildren() {
        return children;
    }

    public void setChildren(List<PackageVO> children) {
        this.children = children;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public String getClassInfoKey() {
        return classInfoKey;
    }

    public void setClassInfoKey(String classInfoKey) {
        this.classInfoKey = classInfoKey;
    }

    public int getClassUseCount() {
        return classUseCount;
    }

    public void setClassUseCount(int classUseCount) {
        this.classUseCount = classUseCount;
    }

    public int getUsedClassCount() {
        return usedClassCount;
    }

    public void setUsedClassCount(int usedClassCount) {
        this.usedClassCount = usedClassCount;
    }
}
