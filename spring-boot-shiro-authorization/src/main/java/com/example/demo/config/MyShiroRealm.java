package com.example.demo.config;

import com.example.demo.dao.UserMapper;
import com.example.demo.dao.UserPermissionMapper;
import com.example.demo.dao.UserRoleMapper;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.omg.CORBA.portable.UnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LiYingChun
 * @date 2019/7/31
 */
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserPermissionMapper userPermissionMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 获取用户角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        logger.info("用户 {} 获得权限 ------- doGetAuthorizationInfo", username);

        // 获取用户角色集
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roleList = userRoleMapper.findByUserName(username);
        Set<String> roleSet = new HashSet<>();
        for (Role role : roleList) {
            roleSet.add(role.getName());
        }
        simpleAuthorizationInfo.setRoles(roleSet);

        //获取用户权限集
        List<Permission> permissions = userPermissionMapper.findByUserName(username);
        Set<String> permissionSet = new HashSet<>();
        for (Permission p : permissions) {
            permissionSet.add(p.getName());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;

    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名和密码
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        logger.info("用户 {} 登录认证 --------- doGetAuthenticationInfo", username);

        // 通过用户名到数据库查询信息
        logger.info("---------根据 {} 查询用户-----------", username);
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误");
        }
        if ("0".equals(user.getStatus())) {
            throw new LockedAccountException("账户被锁定，请联系管理员");
        }
        return new SimpleAuthenticationInfo(user, password, username);
    }
}
