package com.wenda.Model;

import java.util.Date;

/**
 * @auther 张伟豪
 * @create 2019/6/23-15:21
 */
public class Question {
    private int id;
    private String title;
    private String content;
    private int userId;
    private Date createDate;
    private int commentCount;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date creatDate) {
        this.createDate = creatDate;
    }

}
