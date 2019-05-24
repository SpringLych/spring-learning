package com.example.mybatis.mapper;

import com.example.mybatis.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LiYingChun
 * @date 2019/5/24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperAnoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapperAno userMapperAno;

    @Test
    public void getAll() {
        List<User> users = userMapperAno.getAll();
        users.forEach(user ->
                logger.info("user = {}", user)
        );
    }

    @Test
    public void getById() {
        User user = userMapperAno.getById(29L);
        logger.info("user = {}", user);
    }

}