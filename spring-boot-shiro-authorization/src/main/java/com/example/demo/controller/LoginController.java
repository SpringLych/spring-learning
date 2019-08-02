package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.util.MD5Util;
import com.example.demo.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LiYingChun
 * @date 2019/8/1
 */
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseUtil login(String username, String password) throws LockedAccountException, IncorrectCredentialsException {
        password = MD5Util.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResponseUtil.ok();
        } catch (UnknownAccountException e) {
            return ResponseUtil.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseUtil.error("认证失败！");
        }
    }


    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:index";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }

    @GetMapping("/index")
    public String index(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.info("-------------用户名字：{}-----------", user.getUsername());
        model.addAttribute("user", user);
        return "index";
    }
}
