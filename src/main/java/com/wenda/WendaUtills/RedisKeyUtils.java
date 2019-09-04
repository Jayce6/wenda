package com.wenda.WendaUtills;

/**
 * @auther zwh
 * @create 2019/6/29-14:13
 */
public class RedisKeyUtils {
    private static String  SPLIT =":";
    private static String  BIZ_LIKE="LIKE";
    private static String  BIZ_DISLIKE="DISLIKE";
    private static String  BIZ_EventQueue="EVENT_QUEUE";
    private static String  BIZ_FOLLOWER="FOLLOWER";
    private static String  BIZ_FOLLOWEE="FOLLOWEE";

    public static  String getLikeKey(int entityType,int entityId){
        return  BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
    public static  String getDislikeKey(int entityType,int entityId){
        return  BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getEventQueueKey(){
        return BIZ_EventQueue;
    }

    public static String getFollowerKey(int entityType,int entityId ){
        return BIZ_FOLLOWER+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getFolloweeKey(int entityType,int userId){
        return BIZ_FOLLOWEE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(userId);
    }
}
