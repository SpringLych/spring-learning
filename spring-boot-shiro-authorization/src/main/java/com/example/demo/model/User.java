package com.example.demo.model;

import lombok.Data;

import java.util.Date;

/**
 * @author LiYingChun
 * @date 2019/8/1
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Date createTime;
    private String status;
}
