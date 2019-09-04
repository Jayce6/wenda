package com.wenda.Service;

import com.wenda.Dao.MessageDao;
import com.wenda.Model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import sun.nio.cs.ext.MS874;

import java.util.List;

/**
 * @auther 张伟豪
 * @create 2019/6/27-21:12
 */
@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitiveService sensitiveService;

    public int addMessage(Message message)  {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message) >0? message.getId():0;
    }

    public List<Message> getMessageDetail(String conversionId,int offset,int limit)  {
        return messageDao.getConversionDetail(conversionId,offset,limit);
    }
    public List<Message> getMessageList(int userId,int offset, int limit){
        return messageDao.getMessageList(userId,offset,limit);
    }
    public int getConversationUnreadCount(String conversionId,int userId){
        return messageDao.getConversationUnreadCount(conversionId,userId);
    }

    public int setHasRead(String conversionId,int userId){
        return messageDao.setHasRead(conversionId,userId);
    }
}
