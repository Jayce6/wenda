package com.wenda.Controller;


import com.wenda.Dao.ViewObject;
import com.wenda.Model.EntityType;
import com.wenda.Model.HostHolder;
import com.wenda.Model.Question;
import com.wenda.Model.User;
import com.wenda.Service.CommentService;
import com.wenda.Service.FollowService;
import com.wenda.Service.QuestionService;
import com.wenda.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 张伟豪
 * @create 2019/6/23-17:03
 */
@Controller
public class HomeController {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;

    private List<Map> getQuestion(int userId,int offset,int limit)
    {
        List<Question> latesetQuestions = questionService.getLatesetQuestions(userId, offset, limit);
        List<Map> list = new ArrayList<>();
        for (Question question: latesetQuestions) {
            Map<String,Object> map= new HashMap();
            map.put("user",userService.getUser(question.getUserId()));
            map.put("followCount",followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId()));
            map.put("question",question);
            list.add(map);
        }
       return list;
    }

    private List<Map> getQuestionAndFans(int userId,int offset,int limit)
    {
        List<Question> latesetQuestions = questionService.getLatesetQuestions(userId, offset, limit);
        List<Map> list = new ArrayList<>();
        for (Question question: latesetQuestions) {
            Map<String,Object> map= new HashMap();
            User user = userService.getUser(question.getUserId());
            map.put("user",user);
            map.put("fans",followService.getFollowerCount(EntityType.ENTITY_USER,user.getId()));
            map.put("question",question);
            list.add(map);
        }
        return list;
    }

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    public String index(Model model){

        model.addAttribute("vos",getQuestionAndFans(0,0,10));

        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.POST,RequestMethod.GET})
    public String userIndex(Model model, @PathVariable("userId")int userId){

        model.addAttribute("vos",getQuestion(userId,0,10));

        Map<String,Object> map = new HashMap<>();
        map.put("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,userId));
        map.put("followeeCount",followService.getFolloweeCount(EntityType.ENTITY_USER,userId));
        map.put("commentCount",commentService.getUserCommentCount(userId));
        map.put("user",userService.getUser(userId));
        if (hostHolder.getUsers()!=null)
        {

            map.put("followed",followService.isFollower(hostHolder.getUsers().getId(), EntityType.ENTITY_USER,userId));
            if (hostHolder.getUsers().getId()==userId)
            {
                map.put("isMySelf",true);
            }else {
                map.put("isMySelf",false);
            }
        }else {
            map.put("followed",false);
            map.put("isMySelf",false);

        }

        model.addAttribute("profileUser",map);

        return "profile";
    }
}
