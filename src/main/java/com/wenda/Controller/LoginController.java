package com.wenda.Controller;

import com.sun.deploy.net.HttpResponse;
import com.wenda.Model.LoginTicket;
import com.wenda.Service.UserService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther 张伟豪
 * @create 2019/6/24-15:02
 */
@Controller
public class LoginController {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    UserService userService;



    @RequestMapping(path = {"/reg/"},method = {RequestMethod.POST,RequestMethod.GET})
    public String reg(Model model,
                        @RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpServletResponse httpServletResponse) {

        try {
            Map<String,String> map =userService.register(username,password);
            if (map.containsKey("msg")){
/*                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);*/
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }else {
                model.addAttribute("msg","注册成功！请登录");
                return "login";
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/login/"},method = {RequestMethod.POST,RequestMethod.GET})
    public String login(Model model,
                        @RequestParam(value = "username",required = false)String username,
                        @RequestParam(value="password",required = false)String password,
                        @RequestParam(value="rememberme",defaultValue = "false") boolean rememberme,
                        HttpServletResponse httpServletResponse) {
        try {
            Map<String,String> map =userService.login(username,password);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
                return "redirect:/";
            }else {
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }
        }catch (Exception   e){
            logger.error("登录异常"+e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket")String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

    @RequestMapping(path = {"/relogin"},method = {RequestMethod.GET})
    public String relogin() {
        return "login";
    }


}
