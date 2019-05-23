package com.example.demo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        ArrayList<String> list = new ArrayList<>(10);
        list.add("Hello");
        list.add("Ming");
        String one = list.get(0);
        System.out.println(one);

        for (String a : list) {
            System.out.println(a);
        }
    }

    @Test
    public void logTest(){
        log.error("error");
    }

}
