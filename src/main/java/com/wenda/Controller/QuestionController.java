package com.wenda.Controller;

import com.alibaba.fastjson.JSONObject;
import com.wenda.Dao.ViewObject;
import com.wenda.Model.*;
import com.wenda.Service.CommentService;
import com.wenda.Service.LikeService;
import com.wenda.Service.QuestionService;
import com.wenda.Service.UserService;
import com.wenda.WendaUtills.JsonUtils;
import com.wenda.WendaUtills.WendaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.*;

/**
 * @auther 张伟豪
 * @create 2019/6/26-15:50
 */
@Controller
public class QuestionController {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;
    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title")String titlt,@RequestParam("content")String content){
        try{
            Question question = new Question();
            question.setTitle(titlt);
            question.setContent(content);
            question.setCreateDate(new Date());
            question.setCommentCount(0);
            if(hostHolder.getUsers()==null){
                question.setUserId(WendaUtils.ANONUMOUS_USERID);
            }else {
                question.setUserId(hostHolder.getUsers().getId());
            }
            if (questionService.addQuestion(question)>0){
                return JsonUtils.getJSONString(0);
            }

        }catch (Exception e) {
            logger.error("增加提问失败！"+e.getMessage());
        }
        return JsonUtils.getJSONString(1,"失败！");
    }


    @RequestMapping(value = "/question/{qid}",method = {RequestMethod.GET})
    public String questionDetail(Model model,@PathVariable("qid")int qid)  {
        Question question = questionService.selectById(qid);
        question.setId(qid);
        model.addAttribute("question",question);

        List<Comment> commentlist = commentService.getComment(qid, EntityType.ENTITY_QUESTION);


        List<Map> comments = new LinkedList<>();

        for(Comment comment : commentlist)  {
            Map map = new HashMap();
            map.put("comment",comment);
            if (hostHolder.getUsers()==null)
            {
                map.put("liked",0);
            }else {
                map.put("liked",likeService.getLikeStatus(hostHolder.getUsers().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            map.put("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));
            map.put("user",userService.getUser(comment.getUserId()));
            comments.add(map);
        }



       /* User user = userService.getUser(question.getUserId());*/
        model.addAttribute("comments",comments);
            return "detail";
    }
}
