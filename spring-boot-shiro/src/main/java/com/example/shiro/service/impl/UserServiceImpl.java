package com.example.shiro.service.impl;

import com.example.shiro.mapper.UserMapper;
import com.example.shiro.model.User;
import com.example.shiro.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author LiYingChun
 * @date 2019/5/27
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUsername(String name){
        return userMapper.findByUsername(name);
    }
}
