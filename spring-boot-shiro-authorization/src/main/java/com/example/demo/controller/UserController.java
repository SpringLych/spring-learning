package com.example.demo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LiYingChun
 * @date 2019/8/1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * RequiresPermissions 当前需要权限user:a 或 user:b
     */
    @RequiresPermissions("user:user")
    @RequestMapping("list")
    public String userList(Model model) {
        model.addAttribute("value", "获取用户信息");
        return "user";
    }

    @RequiresPermissions("user:add")
    @RequestMapping("add")
    public String addUser(Model model) {
        model.addAttribute("value", "添加用户");
        return "user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping("delete")
    public String deleteUser(Model model) {
        model.addAttribute("value", "删除用户");
        return "user";
    }
}
