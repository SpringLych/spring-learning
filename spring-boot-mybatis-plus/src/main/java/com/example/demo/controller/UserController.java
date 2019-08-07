package com.example.demo.controller;


import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author spring
 * @since 2019-08-07
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/insert")
    public void insert() {
        User user = new User();
        user.setName("Clerk");
        user.setCode("007");
        user.setStatus("1");
        int res = userDao.insert(user);
        log.info("---controller 插入 {} 个", res);
    }
}

