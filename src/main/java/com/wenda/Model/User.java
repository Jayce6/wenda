package com.wenda.Model;

import org.springframework.stereotype.Repository;

/**
 * @auther 张伟豪
 * @create 2019/6/22-17:08
 */
@Repository
public class User {

    private int id;
    private  String name;
    private  String password;
    private  String salt;
    private  String headUrl;

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

}
