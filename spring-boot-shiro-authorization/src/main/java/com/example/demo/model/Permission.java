package com.example.demo.model;

import lombok.Data;

/**
 * @author LiYingChun
 * @date 2019/7/29
 * 用户权限表
 */
@Data
public class Permission {
    private static final long serialVersionUID = -227437593919820521L;
    private Integer id;
    private String url;
    private String name;
}
