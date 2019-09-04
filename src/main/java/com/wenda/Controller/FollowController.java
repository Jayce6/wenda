package com.wenda.Controller;

import com.alibaba.fastjson.JSON;
import com.wenda.Model.*;
import com.wenda.Service.CommentService;
import com.wenda.Service.FollowService;
import com.wenda.Service.QuestionService;
import com.wenda.Service.UserService;
import com.wenda.WendaUtills.JsonUtils;
import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * @auther Zwh
 * @create 2019/7/1-15:44
 */
@Controller
public class FollowController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/followUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String followUser(@Param("userId")int userId)  {
            if (hostHolder.getUsers()==null){
                return JsonUtils.getJSONString(999);
            }
        boolean ret = followService.addFollow(hostHolder.getUsers().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel().setEventType(EventType.FOLLOW).setActorId(hostHolder.getUsers().getId())
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerId(userId).setEntityId(userId));

        return JsonUtils.getJSONString(ret?0:1,String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_USER,userId)));
    }


    @RequestMapping(path = {"/unfollowUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@Param("userId")int userId)  {
        if (hostHolder.getUsers()==null){
            return JsonUtils.getJSONString(999);
        }
        boolean ret = followService.unFollow(hostHolder.getUsers().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel().setEventType(EventType.UNFOLLOW).setActorId(hostHolder.getUsers().getId())
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerId(userId).setEntityId(userId));
        return JsonUtils.getJSONString(ret?0:1,String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_USER,userId)));
    }

    @RequestMapping(path = {"/followQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@Param("questionId")int questionId)  {
        if (hostHolder.getUsers()==null){
            return JsonUtils.getJSONString(999);
        }
        Question question = questionService.selectById(questionId);
        if (question==null) {
            JsonUtils.getJSONString(1,"问题不存在！");
        }
        boolean ret = followService.addFollow(hostHolder.getUsers().getId(), EntityType.ENTITY_QUESTION,questionId);
        eventProducer.fireEvent(new EventModel().setEventType(EventType.FOLLOW).setActorId(hostHolder.getUsers().getId())
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerId(questionId).setEntityId(questionId));
        Map<String,Object> info = new HashMap<>();
        info.put("name",hostHolder.getUsers().getName());
        info.put("heafUrl",hostHolder.getUsers().getHeadUrl());
        info.put("id",hostHolder.getUsers().getId());
        info.put("count",info);
        return JsonUtils.getJSONString(ret?0:1,String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId)));
}

    @RequestMapping(path = {"/unfollowQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@Param("questionId")int questionId)  {
        if (hostHolder.getUsers()==null){
            return JsonUtils.getJSONString(999);
        }
        Question question = questionService.selectById(questionId);
        if (question==null) {
            JsonUtils.getJSONString(1,"问题不存在！");
        }
        boolean ret = followService.unFollow(hostHolder.getUsers().getId(), EntityType.ENTITY_QUESTION, questionId);
        eventProducer.fireEvent(new EventModel().setEventType(EventType.UNFOLLOW).setActorId(hostHolder.getUsers().getId())

                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerId(questionId).setEntityId(questionId));

        Map<String,Object> info = new HashMap<>();
        info.put("name",hostHolder.getUsers().getName());
        info.put("heafUrl",hostHolder.getUsers().getHeadUrl());
        info.put("id",hostHolder.getUsers().getId());
        info.put("count",info);
        return JsonUtils.getJSONString(ret?0:1,String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId)));
    }


    @RequestMapping(path = {"/user/{uid}/followees"},method = {RequestMethod.POST})
    public String followees(Model model,@PathVariable("uid")int uid)  {
        List<Integer> followeeIds = followService.getFollowees(EntityType.ENTITY_USER, uid, 0, 10);
        if (hostHolder.getUsers()!=null){
            model.addAttribute("followees",getUserInfo(hostHolder.getUsers().getId(),followeeIds));
        }else {
            model.addAttribute("followees",getUserInfo(0,followeeIds));
        }
        return "followee";
    }

    @RequestMapping(path = {"/user/{uid}/followers"},method = {RequestMethod.POST})
    public String followers(Model model,@PathVariable("uid")int uid)  {
        List<Integer> followerIds = followService.getFollowers(EntityType.ENTITY_USER, uid, 0, 10);
        if (hostHolder.getUsers()!=null){
            model.addAttribute("followers",getUserInfo(hostHolder.getUsers().getId(),followerIds));
        }else {
            model.addAttribute("followers",getUserInfo(0,followerIds));
        }
        return "follower";

    }

    public List<Map> getUserInfo(int localUserId,List<Integer> UserIds){
        List<Map> userInfos = new ArrayList<>();
        for (Integer uid: UserIds) {
            User user = userService.getUser(uid);
            if(user==null){
                continue;
            }
            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            map.put("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,uid));
            map.put("followeeCount",followService.getFolloweeCount(EntityType.ENTITY_USER,uid));
            if (localUserId!=0){
                map.put("followed",followService.isFollower(localUserId,EntityType.ENTITY_USER,uid));
            }else {
                map.put("followed",false);
            }
            userInfos.add(map);
        }
        return userInfos;
    }

}
