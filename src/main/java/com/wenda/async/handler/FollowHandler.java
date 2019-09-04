package com.wenda.async.handler;

import com.wenda.Model.EntityType;
import com.wenda.Model.Message;
import com.wenda.Model.User;
import com.wenda.Service.MessageService;
import com.wenda.Service.UserService;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @auther Zwh
 * @create 2019/7/1-16:50
 */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel eventModel) {
        Message message =  new Message();
        message.setFormId(eventModel.getActorId());
        message.setToId(eventModel.getEntityOwnerId());//eventModel.getEntityOwnerId()
        message.setHasRead(0);
        message.setCreateDate(new Date());
        User user = userService.getUser(eventModel.getActorId());
        if (eventModel.getEntityType() == EntityType.ENTITY_QUESTION){
            message.setContent("用户:"+ user.getName()+",关注了你的问题，" +
                    "http://127.0.0.1/question/"+eventModel.getEntityId());
        }else if (eventModel.getEntityType() == EntityType.ENTITY_USER){
            message.setContent("用户:"+ user.getName()+",关注了你，" +
                    "http://127.0.0.1/user/"+eventModel.getActorId());
        }
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
       return Arrays.asList(EventType.FOLLOW,EventType.UNFOLLOW);
    }
}
