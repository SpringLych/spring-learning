package com.example.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author LiYingChun
 * @date 2019/5/27
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 首页
     */
    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * 登录地址
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录接口
     *
     * @return 状态信息，或成功页面视图
     */
    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        String info = null;

        // 封装Token信息 = 用户名+密码
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // shiro subject 实例
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            info = String.valueOf(subject.isAuthenticated());
            model.addAttribute("info", "登录状态 ==>" + info);
            return "/index";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            info = "未知账户异常";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            info = "账户名密码错误";
        } catch (Exception e) {
            e.printStackTrace();
            info = "其他异常";
        }
        model.addAttribute("info", "登录状态 ==>" + info);
        logger.info("登录状态 ==>{}" + info);
        return "login";
    }
}
