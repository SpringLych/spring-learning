# oauth2

oauth2授权码模式学习。

首先使用IDEA创建一个Spring Boot项目，然后在项目上右键 New - module ，创建三个 module：auth-server（授权服务器）、user-server（资源服务器）、client-app（第三方应用），

| 项目        | 端口 | 备注       |
| :---------- | :--- | :--------- |
| auth-server | 8080 | 授权服务器 |
| user-server | 8081 | 资源服务器 |
| client-app  | 8082 | 第三方应用 |

## 搭建授权服务器

首先提供一个 TokenStore 实例，指生成的 token 存储在哪里

```java
@Configuration
public class AccessTokenConfig {
    @Bean
    TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }
}
```



配置授权服务器

```java
@EnableAuthorizationServer
@Configuration
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

    @Autowired
    ClientDetailsService clientDetailsService;

    /**
     * 配置 token 的基本信息
     */
    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(60 * 60 * 2);
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }

    /**
     * 配置令牌端点约束，即该端点谁能访问
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置客户端详细信息，校验客户端，可以将信息存到数据库，本次存入内存
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("baby")
                .secret(new BCryptPasswordEncoder().encode("123"))
                .resourceIds("res1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .redirectUris("http://localhost:8082/index.html");
    }

    /**
     * 配置令牌的访问端点和令牌服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices());
    }

    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}

```



AuthorizationServer 类记得加上 @EnableAuthorizationServer 注解，表示开启授权服务器的自动化配置。

在 AuthorizationServer 类中，主要重写三个 configure 方法

1.  AuthorizationServerSecurityConfigurer 配置令牌端点安全约束。checkTokenAccess 是校检 token 的端点，当资源服务器收到 token 后，需要校检 token 的合法性，就会访问该端点。
2.  ClientDetailsServiceConfigurer 配置客户端详细信息，此处将客户端信息存在内存中，分别配置了客户端的 id，secret，资源id，授权类型，授权范围以及重定向url
3.  AuthorizationServerEndpointsConfigurer 配置令牌的访问端点和令牌服务。authorizationCodeServices 配置**授权码**的存储，此处存在内存中。tokenServices 配置令牌的存储，即 access_token 的存储位置。
4.  AuthorizationServerTokenServices 配置 token 的基本信息



## 搭建资源服务器

配置代码

```java
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 资源服务器和授权服务器是分开的，配置该项
     */
    @Bean
    RemoteTokenServices tokenServices() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        services.setClientId("baby");
        services.setClientSecret("123");
        return services;
    }

    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("res1").tokenServices(tokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated();
    }
}

```



测试接口

```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }

}
```





# 参考

[这个案例写出来，还怕跟面试官扯不明白 OAuth2 登录流程？](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488214&idx=1&sn=5601775213285217913c92768d415eca&chksm=e9c340b6deb4c9a01bc383b2c0ab124358663adf22a58ba385f792224ba532079a028ba92a3d&scene=178#rd)

