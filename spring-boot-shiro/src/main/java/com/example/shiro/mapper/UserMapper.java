package com.example.shiro.mapper;

import com.example.shiro.model.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author LiYingChun
 * @date 2019/5/27
 */
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);
}
