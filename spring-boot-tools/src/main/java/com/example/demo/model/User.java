package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LiYingChun
 * @date 2019/5/23
 */
@ToString(callSuper = true, exclude = {"address"})
public class User {
    private String name;
    private String address;
}
