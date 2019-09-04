package com.wenda.Controller;

import com.wenda.Model.Comment;
import com.wenda.Model.EntityType;
import com.wenda.Model.HostHolder;
import com.wenda.Model.Question;
import com.wenda.Service.CommentService;
import com.wenda.Service.LikeService;
import com.wenda.WendaUtills.JsonUtils;
import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 张伟豪
 * @create 2019/6/29-14:34
 */
@Controller
public class LikeController {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    EventProducer eventProducer;
    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@Param("commentId")int commentId){
        if(hostHolder.getUsers()==null){
            return JsonUtils.getJSONString(999);
        }
        Comment comment= commentService.getCommentById(commentId);

        eventProducer.fireEvent(new EventModel().setActorId(hostHolder.getUsers().getId())
                                                                .setEntityId(commentId)
                                                                .setEntityType(EntityType.ENTITY_COMMENT)
                                                                .setExt("questionId",String.valueOf(comment.getEntityId()))
                                                                .setEntityOwnerId(comment.getUserId()).setEventType(EventType.LIKE));
        ;


        long LikeCount=  likeService.like(hostHolder.getUsers().getId(), EntityType.ENTITY_COMMENT, commentId);


        return JsonUtils.getJSONString(0,String.valueOf(LikeCount));
    }

    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@Param("commentId")int commentId){
        if(hostHolder.getUsers()==null){
            return JsonUtils.getJSONString(999);
        }
        long LikeCount= likeService.dislike(hostHolder.getUsers().getId(), EntityType.ENTITY_COMMENT,commentId);
        return JsonUtils.getJSONString(0,String.valueOf(LikeCount));
    }

}
