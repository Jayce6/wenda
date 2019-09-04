package com.wenda.Model;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther 张伟豪
 * @create 2019/6/24-20:57
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public  User getUsers() {
        return users.get();
    }

    public  void setUsers(User user) {
        users.set(user);
    }

    public void remove(){
        users.remove();
    }
}
