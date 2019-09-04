package com.wenda.Dao;

import com.wenda.Model.Comment;
import com.wenda.Model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther 张伟豪
 * @create 2019/6/26-20:23
 */
@Mapper
@Repository
public interface CommentDao {

    String TABLE_NAME= " comment ";
    String INSERT_FIELDS =" content, user_id, create_date, entity_id, entity_type, status";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;

    @Insert({"INSERT INTO", TABLE_NAME,"(", INSERT_FIELDS,
            ") VALUES(#{content},#{userId},#{createDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"SELECT count(id) FROM",TABLE_NAME,"WHERE entity_id=#{entityId} and entity_type=#{entityType}" })
    int  getCommentCount(@Param("entityId") int entityId,
                         @Param("entityType") int entityType);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE entity_id=#{entityId} and entity_type=#{entityType}"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                                         @Param("entityType") int entityType);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE id=#{commentId}"})
    Comment getCommentById(@Param("commentId")int commentId);


    @Update({"UPDATE comment set status=#{status} where id=#{id}"})
    int  deleteComment(@Param("id") int id,
                  @Param("status") int status);

    @Select({"SELECT count(id) FROM",TABLE_NAME,"WHERE user_id=#{userId}" })
    int  getUserCommentCount(@Param("userId") int userId);
}
