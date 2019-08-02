package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiYingChun
 * @date 2019/7/29
 * 用户角色表
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -227437593919820521L;
    private Integer id;
    private String name;
    private String desc;
}
