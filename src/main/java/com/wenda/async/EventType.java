package com.wenda.async;

/**
 * @auther 张伟豪
 * @create 2019/6/30-9:36
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);


    private int value;
    EventType(int value){
        this.value=value;
    }

    public int getValue(){
        return this.value;
    }

}
