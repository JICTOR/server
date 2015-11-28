package com.fasoo.logic.visual.tree;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.json.JsonUtils;
import com.fasoo.logic.vo.tree.ClassInfoFieldChildrenVO;
import com.fasoo.logic.vo.tree.ClassInfoFieldVO;
import com.fasoo.parser.data.DataClassInfo;
import com.fasoo.parser.data.DataMethod;
import com.fasoo.parser.data.DataVariable;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeVisual {

    public String classInfoToTree(String classInfoKey, DualHashBidiMap classInfoList, DualHashBidiMap fullyQualifiedNameList) {

        if (!fullyQualifiedNameList.containsKey(classInfoKey)) {
            return null;
        }
        DataClassInfo classInfo = (DataClassInfo) classInfoList.get(classInfoKey);
        Map map = getTreeMap(classInfo);
        return JsonUtils.objectToJson(map);
    }

    public String gitTree(String classInfoKey) {

        DataClassInfo classInfo = MongoDBUtils.findClassInfo(classInfoKey);
        if (classInfo == null) {
            return null;
        }
        Map map = getTreeMap(classInfo);
        return JsonUtils.objectToJson(map);
    }

    private List<ClassInfoFieldVO> classInfoToClassInfoFieldList(DataClassInfo classInfo) {

        List<ClassInfoFieldVO> result = new ArrayList<ClassInfoFieldVO>();
        List<ClassInfoFieldChildrenVO> classInfoFieldChildrenList;

        if (!classInfo.getListFieldVariable().isEmpty()) {
            classInfoFieldChildrenList = new ArrayList<ClassInfoFieldChildrenVO>();
            for (DataVariable dataVariable : classInfo.getListFieldVariable()) {
                classInfoFieldChildrenList.add(new ClassInfoFieldChildrenVO(dataVariable.getFullDeclaration(), 10));
            }
            result.add(new ClassInfoFieldVO("ListFieldVariable", classInfoFieldChildrenList));
        }
        if (!classInfo.getListMethod().isEmpty()) {
            classInfoFieldChildrenList = new ArrayList<ClassInfoFieldChildrenVO>();
            for (DataMethod dataMethod : classInfo.getListMethod()) {
                classInfoFieldChildrenList.add(new ClassInfoFieldChildrenVO(dataMethod.getFullDeclaration(), 10));
            }
            result.add(new ClassInfoFieldVO("ListMethod", classInfoFieldChildrenList));
        }
        if (classInfo.getSuperClass() != null) {
            classInfoFieldChildrenList = new ArrayList<ClassInfoFieldChildrenVO>();
            classInfoFieldChildrenList.add(new ClassInfoFieldChildrenVO(classInfo.getSuperClass(), 10));
            result.add(new ClassInfoFieldVO("SuperClass", classInfoFieldChildrenList));
        }
        if (!classInfo.getSuperInterfaces().isEmpty()) {
            result.add(new ClassInfoFieldVO("SuperClassInterfaces", dataRelationListToClassInfoFieldChildrenList(classInfo.getSuperInterfaces())));
        }
        if (!classInfo.getRelationFieldVar().isEmpty()) {
            result.add(new ClassInfoFieldVO("RelationFieldVariable", dataRelationListToClassInfoFieldChildrenList(classInfo.getRelationFieldVar())));
        }
        if (!classInfo.getRelationMethodReturn().isEmpty()) {
            result.add(new ClassInfoFieldVO("RelationMethodReturn", dataRelationListToClassInfoFieldChildrenList(classInfo.getRelationMethodReturn())));
        }
        if (!classInfo.getRelationMethodParameter().isEmpty()) {
            result.add(new ClassInfoFieldVO("RelationMethodParameter", dataRelationListToClassInfoFieldChildrenList(classInfo.getRelationMethodParameter())));
        }
        if (!classInfo.getRelationMethodLocalVar().isEmpty()) {
            result.add(new ClassInfoFieldVO("RelationMethodLocalVariable", dataRelationListToClassInfoFieldChildrenList(classInfo.getRelationMethodLocalVar())));
        }
        if (!classInfo.getRelationStatic().isEmpty()) {
            result.add(new ClassInfoFieldVO("RelationStatic", dataRelationListToClassInfoFieldChildrenList(classInfo.getRelationStatic())));
        }
        if (!classInfo.getRelationAnnotation().isEmpty()) {
            result.add(new ClassInfoFieldVO("RelationAnnotation", dataRelationListToClassInfoFieldChildrenList(classInfo.getRelationAnnotation())));
        }
        return result;
    }

    private List<ClassInfoFieldChildrenVO> dataRelationListToClassInfoFieldChildrenList(List<String> dataRelationList) {

        List<ClassInfoFieldChildrenVO> classInfoFieldChildrenList = new ArrayList<ClassInfoFieldChildrenVO>();
        for (String dataRelation : dataRelationList) {
            classInfoFieldChildrenList.add(new ClassInfoFieldChildrenVO(dataRelation, 10));
        }
        return classInfoFieldChildrenList;
    }

    private Map getTreeMap(DataClassInfo classInfo) {

        Map map = new HashMap();
        map.put("name", classInfo.getFQName());
        map.put("children", classInfoToClassInfoFieldList(classInfo));
        map.put("classType", classInfo.getClassType());
        map.put("javaFile", classInfo.getJavaFileName());
        return map;
    }
}
