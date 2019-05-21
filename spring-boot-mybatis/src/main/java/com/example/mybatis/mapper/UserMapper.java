package com.example.mybatis.mapper;

import com.example.mybatis.model.User;

import java.util.List;

/**
 * @author LiYingChun
 * @date 2019/5/21
 */
public interface UserMapper {
    void insert(User user);

    void update(User user);

    void delete(Long id);

    User getById(Long id);

    List<User> getAll();
}
