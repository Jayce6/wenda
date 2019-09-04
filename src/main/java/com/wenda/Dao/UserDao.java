package com.wenda.Dao;

import com.wenda.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

/**
 * @auther 张伟豪
 * @create 2019/6/22-17:13
 */

@Mapper
@Repository
public interface UserDao {

    String TABLE_NAME= " user ";
    String INSERT_FIELDS =" name, password, salt, head_url ";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;


    @Insert({"INSERT INTO", TABLE_NAME,"(", INSERT_FIELDS,
            ") VALUES(#{name},#{password},#{salt},#{headUrl})"})
     int addUser(User user);


    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE id=#{id}"})
    User selectById(int id);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE name=#{username}"})
    User selectByName(String username);

    @Update({"UPDATE", TABLE_NAME,"SET password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"DELETE FROM ",TABLE_NAME,"WHERE id=#{id}"})
    void deleteById(int id);
}
