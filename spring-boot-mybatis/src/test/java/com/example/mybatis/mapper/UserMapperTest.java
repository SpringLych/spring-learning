package com.example.mybatis.mapper;

import com.example.mybatis.model.User;
import com.example.mybatis.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author LiYingChun
 * @date 2019/5/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        userMapper.insert(new User("aa", "pass1", "man"));
        userMapper.insert(new User("aa", "pass1", "man"));
        userMapper.insert(new User("aa", "pass1", "man"));
        assertEquals(3, userMapper.getAll().size());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void getAll() {

    }
}