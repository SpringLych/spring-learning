package com.example.demo.dao;

import com.example.demo.model.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LiYingChun
 * @date 2019/7/29
 * 用户权限相关
 */
@Mapper
public interface UserPermissionMapper {
    List<Permission> findByUserName(String username);
}
