package com.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther Zwh
 * @create 2019/6/30-9:43
 */
public class EventModel {
    private EventType eventType;
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;

//    public Map<String, String> getExts() {
//        return exts;
//    }
//
//    public void setExts(Map<String, String> exts) {
//        this.exts = exts;
//    }

    private Map<String,String> exts =new HashMap<>();

    public  EventModel(){

    }


    public String getExt(String Key)
    {
        return this.exts.get(Key);
    }

    public EventModel setExt(String key,String value){
        this.exts.put(key,value);
        return this;

    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;

    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;

    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;

    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;

    }
}
