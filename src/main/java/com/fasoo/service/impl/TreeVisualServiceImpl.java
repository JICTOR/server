package com.fasoo.service.impl;

import com.fasoo.logic.visual.tree.TreeVisual;
import com.fasoo.service.TreeVisualService;
import org.springframework.stereotype.Service;

@Service
public class TreeVisualServiceImpl implements TreeVisualService {

    @Override
    public String classInfoToTree(String classInfoKey) {
        TreeVisual treeVisual = new TreeVisual();
        return treeVisual.gitTree(classInfoKey);
    }
}
