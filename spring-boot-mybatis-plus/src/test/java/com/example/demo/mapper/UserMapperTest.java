package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author LiYingChun
 * @date 2019/5/24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectOne() {
        User user = userMapper.selectById(1L);
        logger.info("user = {}", user);
    }

    @Test
    public void testSelectAll() {
        List<User> list = userMapper.selectList(null);
        list.forEach(user -> {
            logger.info("user = {}", user);
        });

    }

    @Test
    public void update() {
        User user = userMapper.selectById(2L);
        assertThat(user.getAge()).isEqualTo(20);

        userMapper.update(
                null,
                Wrappers.<User>lambdaUpdate().set(User::getEmail, "123@123").eq(User::getId, 2)
        );
        assertThat(userMapper.selectById(2).getEmail()).isEqualTo("123@123");
    }

    @Test
    public void testDelete() {
        userMapper.deleteById(2L);
    }


    @Test
    public void testPage() {
        logger.info("----------分页---------");
        Page<User> page = new Page<>(1, 2);
        IPage<User> userIPage = userMapper.selectPage(page,
                new QueryWrapper<User>().gt("age", 6));
        logger.info("总条数：{}", userIPage.getTotal());
        logger.info("当前页数：{}", userIPage.getCurrent());
        logger.info("当前每页显示数：{}", userIPage.getSize());
        logger.info("----------分页---------");
    }
}