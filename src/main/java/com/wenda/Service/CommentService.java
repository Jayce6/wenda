package com.wenda.Service;

import com.wenda.Dao.CommentDao;
import com.wenda.Model.Comment;
import com.wenda.WendaUtills.WendaUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @auther 张伟豪
 * @create 2019/6/26-20:47
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    SensitiveService sensitiveService;
    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDao.addComment(comment)>0? comment.getId():0;
    }

    public int getCommentCount(int entityId,int entityType)  {
        return commentDao.getCommentCount(entityId,entityType);
    }

    public List<Comment> getComment(int entityId, int entityType)  {
       return commentDao.selectCommentByEntity(entityId,entityType);
    }

    public boolean deleteComment(int id)  {
        return commentDao.deleteComment(id,1)>0;
    }

    public Comment  getCommentById(int commentId){
        return commentDao.getCommentById(commentId);
    }

    public int  getUserCommentCount(int userId){
     return commentDao.getUserCommentCount(userId);
    }
}

