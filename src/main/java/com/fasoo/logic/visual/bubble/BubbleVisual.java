package com.fasoo.logic.visual.bubble;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.json.JsonUtils;
import com.fasoo.logic.vo.bubble.UseUsedVO;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BubbleVisual {

    public String useClassInfoJson(String classInfoKey, Map<String, Set<String>> classUseMap, DualHashBidiMap fullyQualifiedNameList) {

        if (!classUseMap.containsKey(classInfoKey)) {
            return null;
        }
        String target = fullyQualifiedNameList.get(classInfoKey).toString();
        return JsonUtils.objectToJson(getUseList(classUseMap.get(classInfoKey), target));
    }
    private List<UseUsedVO> getUseList(Set<String> classUseSet, String target) {

        List<UseUsedVO> result = new ArrayList<UseUsedVO>();
        UseUsedVO use;
        for (String classUse : classUseSet) {
            use = new UseUsedVO();
            use.setSource(classUse);
            use.setTarget(target);
            result.add(use);
        }
        return result;
    }

    public String usedClassInfoJson(String classInfoKey, Map<String, Set<String>> usedClassMap, DualHashBidiMap fullyQualifiedNameList) {

        if (!usedClassMap.containsKey(classInfoKey)) {
            return null;
        }
        String source = fullyQualifiedNameList.get(classInfoKey).toString();
        return JsonUtils.objectToJson(getUsedList(usedClassMap.get(classInfoKey), source));
    }

    private List<UseUsedVO> getUsedList(Set<String> usedClassSet, String source) {

        List<UseUsedVO> result = new ArrayList<UseUsedVO>();
        UseUsedVO used;
        for (String usedClass : usedClassSet) {
            used = new UseUsedVO();
            used.setSource(source);
            used.setTarget(usedClass);
            result.add(used);
        }
        return result;
    }
}
