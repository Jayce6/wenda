package com.wenda.Controller;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import com.wenda.Dao.CommentDao;
import com.wenda.Model.Comment;
import com.wenda.Model.EntityType;
import com.wenda.Model.HostHolder;
import com.wenda.Model.Question;
import com.wenda.Service.CommentService;
import com.wenda.Service.QuestionService;
import com.wenda.WendaUtills.WendaUtils;
import org.apache.ibatis.annotations.Param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.html.parser.Entity;
import java.util.Date;

/**
 * @auther 张伟豪
 * @create 2019/6/26-20:56
 */

@Controller
public class CommentController {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;
        @RequestMapping(path = {"/addComment"},method = {RequestMethod.POST})
        public String addComment(@Param("questionId")int questionId,
                                 @Param("content")String content)  {
            try {
                Comment comment =new Comment();
                if (hostHolder.getUsers()==null)  {
                    return "redirect:/";
                }else {
                    comment.setUserId(hostHolder.getUsers().getId());
                }
                comment.setContent(content);
                comment.setCreateDate(new Date());
                comment.setEntityId(questionId);
                comment.setEntityType(EntityType.ENTITY_QUESTION);
                comment.setStatus(0);
                commentService.addComment(comment);
                int commentCount = commentService.getCommentCount(questionId, comment.getEntityType());
                questionService.updateQuestionCommentCount(questionId,commentCount);

            }catch (Exception e) {
                logger.error("评论失败！"+e.getMessage());
            }
            return "redirect:/question/"+questionId;
        }
}
