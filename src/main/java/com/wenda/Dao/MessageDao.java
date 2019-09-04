package com.wenda.Dao;

/**
 * @auther 张伟豪
 * @create 2019/6/27-20:57
 */

import com.wenda.Model.Message;
import com.wenda.Model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface MessageDao {
    String TABLE_NAME= " message";
    String INSERT_FIELDS =" formid, toid, content, conversion_id, create_date, has_read";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;


    @Insert({"INSERT INTO", TABLE_NAME,"(", INSERT_FIELDS,
            ") VALUES(#{formid},#{toid},#{content},#{conversionId},#{createDate},#{hasRead})"})
    int addMessage(Message message);


    @Select({"SELECT ",INSERT_FIELDS,",count(id) as id FROM(SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME,
            "where formid =#{userId} or toid=#{userId} ORDER BY create_date DESC) tt" + " GROUP BY conversion_id ORDER BY create_date desc LIMIT #{offset},#{limit}"})
    List<Message> getMessageList(@Param("userId") int userId,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE conversion_id =#{conversionId}" +
            "order by create_date desc limit #{offset},#{limit}"})
    List<Message> getConversionDetail(@Param("conversionId")String conversionId,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    @Select({"SELECT COUNT(id) FROM ",TABLE_NAME,"WHERE has_read=0 and conversion_id=#{conversionId} and toid =#{userId}"})
    int getConversationUnreadCount(@Param("conversionId")String conversionId,
                                   @Param("userId")int userId);

    @Update({"UPDATE",TABLE_NAME,"SET has_read = 1 WHERE conversion_id=#{conversionId} and toid=#{userId}"})
    int setHasRead(@Param("conversionId")String conversionId,
                   @Param("userId")int userId);

}
