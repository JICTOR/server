package com.fasoo.logic.utils.db;

import com.fasoo.logic.utils.json.JsonUtils;
import com.fasoo.logic.vo.user.GitUserVO;
import com.fasoo.logic.vo.git.GitCommitVO;
import com.fasoo.logic.vo.user.ProjectUserVO;
import com.fasoo.parser.data.DataClassInfo;
import com.fasoo.parser.gitTest.GitDiffVO;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO : 함수 너무 많음 분리하기
public class MongoDBUtils {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection mongoDatabaseCollection;

    //Connect
    public static void connect(String dataBaseName) {
        mongoClient = new MongoClient("localhost", 27017);
        mongoDatabase = mongoClient.getDatabase(dataBaseName);
    }

    public static void disconnect() {
        mongoClient.close();
    }
    //Connect

    //init
    public static void initCollection() {
        setMongoDatabaseCollection("classInfoCollection");
        mongoDatabaseCollection.drop();
        setMongoDatabaseCollection("commitCollection");
        mongoDatabaseCollection.drop();
    }
    //init

    //insert
    public static void insertProjectUser(ProjectUserVO projectUser) {
        setMongoDatabaseCollection("projectUserCollection");
        Document query = new Document();
        query.put("_id", projectUser.getUserKey());
        if (isExistedKey(query)) {
            //TODO : 기존에 있었던 user 의 정보들(연관된 commit 과 classInfo 도?) 다 지우기 (mongodb 내용이랑 파일도)
            mongoDatabaseCollection.updateOne(query, new Document("$set", new Document("user", JSON.parse(JsonUtils.objectToJson(projectUser)))) );
            return;
        }
        query.put("user", JSON.parse(JsonUtils.objectToJson(projectUser)));
        mongoDatabaseCollection.insertOne(query);
    }

    public static void insertGitUser(GitUserVO gitUser) {
        setMongoDatabaseCollection("gitUserCollection");
        Document query = new Document();
        query.put("_id", gitUser.getUserKey());
        if (isExistedKey(query)) {
            //TODO : 기존에 있었던 user 의 정보들(연관된 commit 과 classInfo 도?) 다 지우기 (mongodb 내용이랑 파일도)
            mongoDatabaseCollection.updateOne(query, new Document("$set", new Document("user", JSON.parse(JsonUtils.objectToJson(gitUser)))) );
            return;
        }
        query.put("user", JSON.parse(JsonUtils.objectToJson(gitUser)));
        mongoDatabaseCollection.insertOne(query);
    }

    public static void insertClassInfo(String key, DataClassInfo classInfo) {
        setMongoDatabaseCollection("classInfoCollection");
        Document query = new Document();
        query.put("_id", key);
        if (isExistedKey(query)) {
            return;
        }
        query.put("classInfo", JSON.parse(JsonUtils.objectToJson(classInfo)));
        mongoDatabaseCollection.insertOne(query);
    }

    public static void insertCommit(GitCommitVO commit) {
        setMongoDatabaseCollection("commitCollection");
        Document query = new Document();
        query.put("_id", commit.getGitCommitId());
        if (isExistedKey(query)) {
            return;
        }
        query.put("commit", JSON.parse(JsonUtils.objectToJson(commit)));
        mongoDatabaseCollection.insertOne(query);
    }
    //insert

    //find
    public static DataClassInfo findClassInfo(String key) {
        setMongoDatabaseCollection("classInfoCollection");
        Document iterableFirst = getDocumentIterable(key).first();
        if (iterableFirst == null) {
            return null;
        }
        return JsonUtils.jsonToDataClassInfo(JSON.serialize(iterableFirst.get("classInfo")));
    }

    public static GitCommitVO findCommit(String key) {
        setMongoDatabaseCollection("commitCollection");
        Document iterableFirst = getDocumentIterable(key).first();
        if (iterableFirst == null) {
            return null;
        }
        return JsonUtils.jsonToGitCommitVO(JSON.serialize(iterableFirst.get("commit")));
    }

    public static ProjectUserVO findProjectUser(String userKey) {
        setMongoDatabaseCollection("projectUserCollection");
        Document iterableFirst = getDocumentIterable(userKey).first();
        if (iterableFirst == null) {
            return null;
        }
        return JsonUtils.jsonToProjectUserVO(JSON.serialize(iterableFirst.get("user")));
    }

    public static GitUserVO findGitUser(String userKey) {
        setMongoDatabaseCollection("gitUserCollection");
        Document iterableFirst = getDocumentIterable(userKey).first();
        if (iterableFirst == null) {
            return null;
        }
        return JsonUtils.jsonToGitUserVO(JSON.serialize(iterableFirst.get("user")));
    }

    public static Map<String, Set<String>> findClassUseMapFromCommitId(String commitId){
        GitCommitVO gitCommit = findCommit(commitId);
        if (gitCommit == null) {
            return null;
        }
        return gitCommit.getClassUseMap();
    }

    public static Map<String, Set<String>> findUsedClassMapFromCommitId(String commitId){
        GitCommitVO gitCommit = findCommit(commitId);
        if (gitCommit == null) {
            return null;
        }
        return gitCommit.getUsedClassMap();
    }

    public static List<String> findAllCommitIdFromGitUserId(String userKey) {
        GitUserVO gitUser = findGitUser(userKey);
        if(gitUser == null){
            return null;
        }
        return gitUser.getCommitList();
    }

    public static List<GitDiffVO> findDiffListFromCommitId(String commitId){
        GitCommitVO gitCommit = findCommit(commitId);
        if (gitCommit == null) {
            return null;
        }
        return gitCommit.getDiffList();
    }

    public static DualHashBidiMap findFullyQualifiedNameMapFromCommitId(String commitId){
        GitCommitVO gitCommit = findCommit(commitId);
        if (gitCommit == null) {
            return null;
        }
        return gitCommit.getFullyQualifiedNameMap();
    }

    public static List<String> findFullyQualifiedNameListFromCommitId(String commitId) {
        List<String> result = new ArrayList<String>();
        GitCommitVO gitCommit = findCommit(commitId);
        if (gitCommit == null) {
            return result;
        }
        for (Object fullyQualifiedName : gitCommit.getFullyQualifiedNameMap().values()) {
            result.add(fullyQualifiedName.toString());
        }
        return result;
    }

    public static String findSunburstJsonFromCommitId(String commitId) {
        GitCommitVO gitCommit = findCommit(commitId);
        if (gitCommit == null) {
            return null;
        }
        return gitCommit.getSunburstJson();
    }

    public static DualHashBidiMap findFullyQualifiedNameMapFromProjectUserKey(String userKey) {
        ProjectUserVO projectUser = findProjectUser(userKey);
        if (projectUser == null) {
            return null;
        }
        return projectUser.getFullyQualifiedNameMap();
    }

    public static List<String> findFullyQualifiedNameListFromProjectUserKey(String userKey) {
        List<String> result = new ArrayList<String>();
        ProjectUserVO projectUser = findProjectUser(userKey);
        if (projectUser == null) {
            return result;
        }
        for (Object fullyQualifiedName : projectUser.getFullyQualifiedNameMap().values()) {
            result.add(fullyQualifiedName.toString());
        }
        return result;
    }

    public static Map<String, Set<String>> findClassUseMapFromProjectUserId(String userKey){
        ProjectUserVO projectUser = findProjectUser(userKey);
        if (projectUser == null) {
            return null;
        }
        return projectUser.getClassUseMap();
    }

    public static Map<String, Set<String>> findUsedClassMapFromProjectUserId(String userKey) {
        ProjectUserVO projectUser = findProjectUser(userKey);
        if (projectUser == null) {
            return null;
        }
        return projectUser.getUsedClassMap();
    }

    public static String findSunburstJsonFromProjectUserId(String userKey){
        ProjectUserVO projectUser = findProjectUser(userKey);
        if(projectUser == null){
            return null;
        }
        return projectUser.getSunburstJson();
    }
    //find

    private static FindIterable<Document> getDocumentIterable(String key) {
        Document searchQuery = new Document();
        searchQuery.put("_id", key);
        FindIterable<Document> iterable = mongoDatabaseCollection.find(searchQuery);
        return iterable;
    }

    private static boolean isExistedKey(Document query) {
        if (mongoDatabaseCollection.find(query).first() != null) {
            return true;
        }
        return false;
    }

    private static void setMongoDatabaseCollection(String collectionName) {
        mongoDatabaseCollection = mongoDatabase.getCollection(collectionName);
    }
}
