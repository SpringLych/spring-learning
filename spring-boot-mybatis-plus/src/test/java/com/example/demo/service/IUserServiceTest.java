package com.example.demo.service;

import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LiYingChun
 * @date 2019/8/8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class IUserServiceTest {
    @Autowired
    private IUserService userService;

    /**
     * 批量插入
     */
    @Test
    public void saveMany() {
        int cap = 15;
        List<User> users = new ArrayList<>(cap);
        for (int i = 0; i < cap; ++i) {
            User user = new User();
            user.setName("Jon + " + i);
            user.setStatus("A");
            user.setCode("007" + i);
            users.add(user);
        }
        userService.saveBatch(users);
        log.info("-----批量插入--------");
    }
}