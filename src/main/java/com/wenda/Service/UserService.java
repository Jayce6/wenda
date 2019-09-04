package com.wenda.Service;

import com.wenda.Dao.LoginTicketDao;
import com.wenda.Dao.UserDao;
import com.wenda.WendaUtills.MD5Utils;
import com.wenda.Model.LoginTicket;
import com.wenda.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * @auther 张伟豪
 * @create 2019/6/23-19:05
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUser(int id){
    return userDao.selectById(id);
    }


    public Map login (String username,String password)
    {
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user =null;
        user=userDao.selectByName(username);
        if (user == null){
            map.put("msg","该用户不存在!");
            return map;
        }

        if(!MD5Utils.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","账号密码错误！");
            return map;
        }

            String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }

    //注册页面
    public Map<String,String> register (String username,String password)
    {
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg","用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user=userDao.selectByName(username);
        if (user != null){
            map.put("msg","该用户已经存在!");
            return map;
        }
        User user1= new User();
        user1.setName(username);
        user1.setSalt(UUID.randomUUID().toString().substring(0,5));
        Random random = new Random();
        user1.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png" , random.nextInt(1000)));
        String md5pswd =MD5Utils.MD5(password+user1.getSalt());
        user1.setPassword(md5pswd);
        userDao.addUser(user1);

        return map;

    }

    public String addLoginTicket(int userId)
    {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(3600*24*100+date.getTime());
        loginTicket.setExpried(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(loginTicket);
        return  loginTicket.getTicket();
    }

    public void logout(String ticket)
    {
        loginTicketDao.updateStatus(ticket,1);
    }

    public User getUserByName(String name) {
        return userDao.selectByName(name);
    }

}
