package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LiYingChun
 * @date 2019/8/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void insert() {
        User user = new User();
        user.setName("Jonmm");
        user.setCode("008");
        user.setStatus("0");
        int res = userDao.insert(user);
        log.info("--插入 {} 条", res);
    }

    /**
     * 分页查询测试
     */
    @Test
    public void findPage() {
        // 当前页码 每页数
        Page<User> page = new Page<>(1, 3);
        IPage<User> userIPage = userDao.selectPage(page, "A");
        log.info(userIPage.toString());
        log.info("---总条数：{} ---", userIPage.getTotal());
        log.info("---当前页：{} ---", userIPage.getCurrent());
        log.info("---总页码：{} ---", userIPage.getPages());
        log.info("---每页多少条：{} ---", userIPage.getSize());

        List<User> users = userIPage.getRecords();
        users.forEach(user -> log.info(user.toString()));

    }
}