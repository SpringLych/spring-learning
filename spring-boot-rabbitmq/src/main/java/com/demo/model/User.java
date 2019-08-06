package com.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiYingChun
 * @date 2019/8/6
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -6213198739204404855L;
    private String name;
    private String pass;
}
