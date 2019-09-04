package com.wenda.Controller;

import com.wenda.Dao.UserDao;
import com.wenda.Model.HostHolder;
import com.wenda.Model.Message;
import com.wenda.Model.User;
import com.wenda.Service.MessageService;
import com.wenda.Service.UserService;
import com.wenda.WendaUtills.JsonUtils;
import com.wenda.WendaUtills.WendaUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @auther 张伟豪
 * @create 2019/6/27-21:16
 */
@Controller
public class MessageConteoller {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/msg/addMessage",method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@Param("toId")String toName,
                             @Param("content")String content){
        try {
            if (hostHolder.getUsers()==null)  {
                return JsonUtils.getJSONString  (999,"未登陆");
            }
            User user = userService.getUserByName(toName);
            if (user==null)
            {
                return JsonUtils.getJSONString(1,"用户不存在");
            }
            Message message = new Message();
            message.setFormId(hostHolder.getUsers().getId());
            message.setContent(content);
            message.setCreateDate(new Date());
            message.setToId(user.getId());
            message.setHasRead(0);
            int id = messageService.addMessage(message);
            return JsonUtils.getJSONString(0);
        }catch (Exception e) {
            logger.error("发送失败!!"+e.getMessage());
            return JsonUtils.getJSONString(1,"发送失败");
        }
    }

    @RequestMapping(value = "/msg/detail",method = {RequestMethod.POST,RequestMethod.GET})
    public String getConversionDetail(Model model,@Param("conversationId")String conversionId){
        try {
            List<Message> messageList = messageService.getMessageDetail(conversionId,0,10);
            List<Map> messages = new ArrayList<>();

            for (Message message:messageList){
                HashMap<String,Object> map = new HashMap<>();
                map.put("message",message);
                map.put("user",userService.getUser(message.getFormId()));
                messages.add(map);
            }
            model.addAttribute("messages",messages);
            messageService.setHasRead(conversionId,hostHolder.getUsers().getId());
        }catch (Exception e) {
            logger.error("获取详情失败！"+e.getMessage());

        }
         return "letterDetail";
    }
    @RequestMapping(value = "/msg/list",method = {RequestMethod.GET})
    public String getConversionList(Model model){
        if(hostHolder.getUsers()==null)
        {
            return "redirect:/";
        }
        int localid= hostHolder.getUsers().getId();

        List<Message> messageList1 = messageService.getMessageList(localid, 0, 10);
        List<Map> messageList = new ArrayList<>();

        for (Message message:messageList1)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("message",message);
            int targetId = localid==message.getFormId()?message.getToId():message.getFormId();
            map.put("user",userService.getUser(targetId));
            map.put("unread",messageService.getConversationUnreadCount(message.getConversionId(),localid));
            messageList.add(map);
        }
        model.addAttribute("conversation",messageList);
        return  "letter";
    }
}
