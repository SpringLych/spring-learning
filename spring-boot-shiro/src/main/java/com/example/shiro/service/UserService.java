package com.example.shiro.service;

import com.example.shiro.model.User;

/**
 * @author LiYingChun
 * @date 2019/5/27
 */
public interface UserService {
    User findByUsername(String username);
}
