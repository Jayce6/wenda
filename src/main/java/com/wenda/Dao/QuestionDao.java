package com.wenda.Dao;

import com.wenda.Model.Question;
import com.wenda.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther 张伟豪
 * @create 2019/6/22-17:13
 */

@Mapper
@Repository
public interface QuestionDao {

    String TABLE_NAME= " question ";
    String INSERT_FIELDS =" title, content, create_date, user_id, comment_count ";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;


    @Insert({"INSERT INTO", TABLE_NAME,"(", INSERT_FIELDS,
            ") VALUES(#{title},#{content},#{createDate},#{userId},#{commentCount})"})
     int addQusetion(Question question);

    @Select({"SELECT",INSERT_FIELDS,"FROM",TABLE_NAME,"WsHERE id =#{id}" })
    Question selectById(int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                               @Param("offset") int offset,
                               @Param("limit") int limit);

    @Insert({"UPDATE", TABLE_NAME,"SET comment_count =#{commentCount} WHERE id=#{id}"})
    int updateQusetionCommentcount(int id,int commentCount);

}
