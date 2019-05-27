package com.example.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author LiYingChun
 * @date 2019/5/27
 */
@Controller
public class RouterController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("demo", "一个测试");
        return "index";
    }

}
