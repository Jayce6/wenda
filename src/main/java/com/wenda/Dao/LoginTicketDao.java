package com.wenda.Dao;

import com.wenda.Model.LoginTicket;
import com.wenda.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @auther 张伟豪
 * @create 2019/6/24-14:43
 */
@Mapper
@Repository
public interface LoginTicketDao {

    String TABLE_NAME= " login_ticket ";
    String INSERT_FIELDS =" user_id, expried, ticket, status";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;

    @Insert({"INSERT INTO", TABLE_NAME,"(", INSERT_FIELDS,
            ") VALUES(#{userId},#{expried},#{ticket},#{status})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /*登出修改status*/
    @Update({"UPDATE", TABLE_NAME,"SET status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket")String ticket,@Param("status")int status);


}
