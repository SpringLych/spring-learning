package com.example.demo.dao;

import com.example.demo.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LiYingChun
 * @date 2019/7/29
 * 用户角色
 */
@Mapper
public interface UserRoleMapper {
    List<Role> findByUserName(String username);
}
