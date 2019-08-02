package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LiYingChun
 * @date 2019/8/1
 */
@Mapper
public interface UserMapper {
    User findByUsername(String username);
}
