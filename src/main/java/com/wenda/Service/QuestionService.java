package com.wenda.Service;

import com.wenda.Dao.QuestionDao;
import com.wenda.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @auther 张伟豪
 * @create 2019/6/23-19:05
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;

    public List<Question> getLatesetQuestions(int userId,int offset,int limit){
        return questionDao.selectLatestQuestions(userId,offset,limit);
    }

    public int addQuestion(Question question){

        //html标签过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDao.addQusetion(question)>0 ? question.getId():0;
    }

    public Question selectById(int id)  {
       return questionDao.selectById(id);
    }

    public boolean updateQuestionCommentCount(int id,int commentCount)
    {
        return questionDao.updateQusetionCommentcount(id,commentCount)>0;
    }
}
