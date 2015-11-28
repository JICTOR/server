package com.fasoo.logic.visual.sunburst;

import com.fasoo.logic.utils.json.JsonUtils;
import com.fasoo.logic.vo.sunburst.PackageVO;
import com.fasoo.parser.data.DataClassInfo;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.*;

public class SunburstVisual {

    private DualHashBidiMap classInfoList;
    private DualHashBidiMap fullyQualifiedNameList;

    private Map<String, Set<String>> classUseMap;
    private Map<String, Set<String>> usedClassMap;

    private boolean compileError = false;

    public String classInfoListToSunburstJson(DualHashBidiMap classInfoList, DualHashBidiMap fullyQualifiedNameList) {

        this.classInfoList = classInfoList;
        this.fullyQualifiedNameList = fullyQualifiedNameList;

        PackageVO superPackage = new PackageVO();

        if (fullyQualifiedNameList.isEmpty()) {
            superPackage.initNameAndChildren("");
            return JsonUtils.objectToJson(superPackage);
        }

        superPackage.initNameAndChildren("PROJECT");

        initClassUseMapAndUsedClassMap();

        for (Object fullyQualifiedNameKey : fullyQualifiedNameList.keySet()) {
            String[] packagePaths = fullyQualifiedNameList.get(fullyQualifiedNameKey).toString().split("\\.");
            searchSubPackage(packagePaths, superPackage.getChildren(), 0, fullyQualifiedNameKey.toString());
        }

        return JsonUtils.objectToJson(superPackage);
    }

    private void initClassUseMapAndUsedClassMap() {

        classUseMap = new HashMap<String, Set<String>>();
        usedClassMap = new HashMap<String, Set<String>>();

        for (Object classInfoKey : classInfoList.keySet()) {
            classUseMap.put(classInfoKey.toString(), countClassUse((DataClassInfo) classInfoList.get(classInfoKey)));
            usedClassMap.put(classInfoKey.toString(), new HashSet<String>());
        }

        Set<String> classUseSet;
        for (String classInfoKey : classUseMap.keySet()) {
            classUseSet = classUseMap.get(classInfoKey);
            for (String classUse : classUseSet) {
                if (fullyQualifiedNameList.containsKey(classInfoKey) && fullyQualifiedNameList.containsValue(classUse)
                        && usedClassMap.containsKey(fullyQualifiedNameList.getKey(classUse))) {
                    usedClassMap.get(fullyQualifiedNameList.getKey(classUse)).add(fullyQualifiedNameList.get(classInfoKey).toString());
                    continue;
                }
                System.out.println(">> compileError (used class) : " + classUse);
                System.out.println(">> compileError (use class): " + classInfoKey);
                compileError = true;
            }
        }
    }

    private void searchSubPackage(String[] packagePaths, List<PackageVO> subPackageList, int packagePathIndex, String classInfoKey) {

        if (packagePathIndex == packagePaths.length - 1) {
            subPackageList.add(makeNewClass(packagePaths[packagePathIndex], classInfoKey));
            return;
        }

        if (!subPackageList.isEmpty()) {
            for (int i = 0; i < subPackageList.size(); i++) {
                if (subPackageList.get(i).getName().equals(packagePaths[packagePathIndex])) {
                    searchSubPackage(packagePaths, subPackageList.get(i).getChildren(), ++packagePathIndex, classInfoKey);
                    return;
                }
            }
        }

        subPackageList.add(new PackageVO(packagePaths[packagePathIndex]));
        searchSubPackage(packagePaths, subPackageList.get(subPackageList.size() - 1).getChildren(), ++packagePathIndex, classInfoKey);
    }

    private PackageVO makeNewClass(String name, String classInfoKey) {

        PackageVO result = new PackageVO(name);
        result.setLineCount(((DataClassInfo) classInfoList.get(classInfoKey)).getLineCount());
        result.setClassInfoKey(classInfoKey);
        result.setClassUseCount(classUseMap.get(classInfoKey).size());
        result.setUsedClassCount(usedClassMap.get(classInfoKey).size());
        return result;
    }

    private Set<String> countClassUse(DataClassInfo classInfo) {

        Set<String> classUseSet = new HashSet<String>();
        if (classInfo.getSuperClass() != null) {
            classUseSet.add(classInfo.getSuperClass());
        }
        if (!classInfo.getSuperInterfaces().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getSuperInterfaces()));
        }
        if (!classInfo.getRelationFieldVar().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getRelationFieldVar()));
        }
        if (!classInfo.getRelationMethodReturn().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getRelationMethodReturn()));
        }
        if (!classInfo.getRelationMethodParameter().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getRelationMethodParameter()));
        }
        if (!classInfo.getRelationMethodLocalVar().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getRelationMethodLocalVar()));
        }
        if (!classInfo.getRelationStatic().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getRelationStatic()));
        }
        if (!classInfo.getRelationAnnotation().isEmpty()) {
            classUseSet.addAll(dataRelationListToClassUseSet(classInfo.getRelationAnnotation()));
        }
        return classUseSet;
    }

    private Set<String> dataRelationListToClassUseSet(List<String> dataRelationList) {

        Set<String> result = new HashSet<String>();
        for (String dataRelation : dataRelationList) {
            result.add(dataRelation);
        }
        return result;
    }

    public Map<String, Set<String>> getClassUseMap() {
        return classUseMap;
    }

    public Map<String, Set<String>> getUsedClassMap() {
        return usedClassMap;
    }

    public boolean isCompileError() {
        return compileError;
    }
}
