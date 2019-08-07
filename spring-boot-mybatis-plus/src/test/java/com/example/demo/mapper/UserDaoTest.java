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
        user.setName("Jon");
        user.setCode("009");
        user.setStatus("0");
        int res = userDao.insert(user);
        log.info("--插入 {} 条", res);
    }

    @Test
    public IPage<User> findPage(Page page, Integer status) {
        return userDao.selectPageVo(page, status);
//        log.info("---- {}", );
    }
}