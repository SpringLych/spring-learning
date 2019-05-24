package com.example.mybatis.mapper;

import com.example.mybatis.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LiYingChun
 * @date 2019/5/24
 * 注解版
 */
public interface UserMapperAno {
    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "username", column = "username")
    })
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "username", column = "username")
    })
    User getById(Long id);

    @Insert("INSERT INTO users(username, password, sex) " +
            "values(#{username}, #{password}, #{sex})")
    void insert();

    @Delete("DELETE FROM users WHERE id=#{id}")
    void delete();
}
