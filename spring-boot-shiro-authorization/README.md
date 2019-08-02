在Spring Boot中集成Shiro进行用户的认证过程主要可以归纳为以下三点：

1、定义一个ShiroConfig，然后配置SecurityManager Bean，SecurityManager为Shiro的安全管理器，管理着所有Subject；

2、在ShiroConfig中配置ShiroFilterFactoryBean，其为Shiro过滤器工厂类，依赖于SecurityManager；

3、自定义Realm实现，Realm包含`doGetAuthorizationInfo()`和`doGetAuthenticationInfo()`方法，因为本文只涉及用户认证，所以只实现`doGetAuthenticationInfo()`方法。

## 数据库模型设计

使用RBAC模型设计用户。表结构和数据见`springbootlearn.sql`文件。

其中：user - 用户表；role - 角色表，user_role - 用户角色关联，permisssion - 权限表，role_permission - 角色权限

UserRoleMapper

```mysql
select r.id, r.`name`, r.`describe`
        from T_ROLE r
                 left join T_USER_ROLE ur on r.id = ur.rid
                 left join T_USER u on u.id = ur.user_id
        where u.username = #{username};
```

## 自定义Realm实现

Realm包含`doGetAuthorizationInfo()`和`doGetAuthenticationInfo()`方法。  

- `doGetAuthorizationInfo`涉及用户权限
- `doGetAuthenticationInfo()`涉及用户认证；

```java
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
```

在获取用户权限的方法中，首先获取当前登录用户的角色和权限集，然后保存到`simpleAuthorizationInfo`对象，并返回给Shiro，Shiro中便存储了用户的角色和权限信息。

## 配置ShiroConfig

Spring的java配置方式是通过@Configuration 和 @Bean这两个注释实现的：

@Configuration：作用于类上，相当于一个xml配置文件

@Bean 作用于方法上，相当于xml配置中的

shiro中的权限相关注解：

```java
// 表示当前Subject已经通过login进行了身份验证；即Subject.isAuthenticated()返回true。
@RequiresAuthentication  
 
// 表示当前Subject已经身份验证或者通过记住我登录的。
@RequiresUser  

// 表示当前Subject没有身份验证或通过记住我登录过，即是游客身份。
@RequiresGuest  

// 表示当前Subject需要角色admin和user。  
@RequiresRoles(value={"admin", "user"}, logical= Logical.AND)  

// 表示当前Subject需要权限user:a或user:b。
@RequiresPermissions (value={"user:a", "user:b"}, logical= Logical.OR)
```

权限相关注解

```java
/**
     * 设置启用Shiro权限相关注解
     */
@Bean
public AuthorizationAttributeSourceAdvisor sourceAdvisor(SecurityManager manager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(manager);
    return advisor;
}
```

securityManager()的实现

```java’
@Bean
public SecurityManager securityManager() {
	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	securityManager.setRealm(shiroRealm());
	securityManager.setRememberMeManager(rememberMeManager());
	return securityManager;
}
```

## 编写Controller

```java
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
```

## 错误信息

> UnavailableSecurityManagerException: No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.

在securityManager()中实现的是**DefaultSecurityManager**而不是**DefaultWebSecurityManager**，应该要实现**DefaultWebSecurityManager**。实现后问题解决。

> org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.example.demo.dao.UserMapper.findByUsername

注意要在application.yml中配置MyBatis的mapper扫描路径。

## 参考

- [Spring Boot Shiro权限控制](https://mrbird.cc/Spring-Boot-Shiro Authorization.html)