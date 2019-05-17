package com.demo.first.controller;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author LiYingChun
 * @date 2019/5/12
 */
public class HelloWorldControllerTest {

    @Test
    public void hello() {
        assertEquals("Hello World", new HelloWorldController().hello());
    }
}