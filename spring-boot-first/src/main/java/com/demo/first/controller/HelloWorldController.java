package com.demo.first.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiYingChun
 * @date 2019/5/12
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String hello(){
        return "Hello World";
    }
}
