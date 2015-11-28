package com.fasoo.logic.vo.tree;

import java.util.List;

public class ClassInfoFieldVO {
    private String name;
    private List<ClassInfoFieldChildrenVO> children;

    public ClassInfoFieldVO(String name, List<ClassInfoFieldChildrenVO> children) {
        this.name = name;
        this.children = children;
    }
}
