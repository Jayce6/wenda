package com.wenda.async.handler;

import com.sun.xml.internal.ws.api.model.MEP;
import com.wenda.Model.Message;
import com.wenda.Model.User;
import com.wenda.Service.MessageService;
import com.wenda.Service.UserService;
import com.wenda.WendaUtills.WendaUtils;
import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @auther Zwh
 * @create 2019/6/30-11:22
 */
@Component
public class LikeHandler implements EventHandler {
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
        message.setContent("用户:"+ user.getName()+",赞了你的评论，" +
                "http://127.0.0.1/question/"+eventModel.getExt("questionId"));


        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
