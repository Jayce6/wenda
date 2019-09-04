package com.wenda.Interceptor;

import com.wenda.Dao.LoginTicketDao;
import com.wenda.Dao.UserDao;
import com.wenda.Model.HostHolder;
import com.wenda.Model.LoginTicket;
import com.wenda.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @auther 张伟豪
 * @create 2019/6/24-17:22
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String ticket = null;
    if(request.getCookies()!=null){
        for(Cookie cookie :request.getCookies()){
            if(cookie.getName().equals("ticket")) {
                ticket = cookie.getValue();
                break;
            }
        }

    }
    if(ticket!=null)
    {
        LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
        if(loginTicket==null || loginTicket.getExpried().before(new Date())||loginTicket.getStatus()!=0)
        {
            return true;
        }

        User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUsers(user);
    }

    return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null){
            modelAndView.addObject("user",hostHolder.getUsers());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.remove();
    }
}
