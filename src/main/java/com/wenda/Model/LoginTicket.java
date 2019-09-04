package com.wenda.Model;

import java.util.Date;

/**
 * @auther 张伟豪
 * @create 2019/6/24-14:41
 */
public class LoginTicket {
    private  int id;
    private  int userId;
    private Date expried;
    private int status;
    private String ticket;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpried() {
        return expried;
    }

    public void setExpried(Date expried) {
        this.expried = expried;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
