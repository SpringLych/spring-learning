package com.example.shiro.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author LiYingChun
 * @date 2019/5/27
 */
@Data
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
}
