# Spring Security OAuth2结合 JWT实现无状态登录



# 授权服务器

WebSecurityConfig

```java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("123")
            .roles("admin");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
```

* 在 PasswordEncoder 中设置为不需要对密码加密
* 内存中配置用户和密码



 AuthorizationServer

```java
@EnableAuthorizationServer
@Configuration
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 设置token由jwt产生
     */
    @Bean
    JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 对Jwt签名时增加一个秘钥
     */
    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("baby");
        return converter;
    }


    /**
     * 配置客户端详细信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("baby")
            .secret("123")
            .scopes("all")
            .authorizedGrantTypes("password",
                                  "authorization_code",
                                  "refresh_token");
    }

    /**
     * 配置令牌点约束
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
            .tokenStore(jwtTokenStore())
            .accessTokenConverter(accessTokenConverter());
    }

}

```





# 资源服务器配置

 ResourceServerConfig 

```java
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("admin")
            .anyRequest().authenticated();
    }
}
```



Controller

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

application.yaml

```yaml
server:
  port: 8081

security:
  oauth2:
    resource:
      jwt:
        key-value: baby

```

- security.oauth2.resource.jwt.key-value：设置签名key 保持和授权服务器一致。
- security.oauth2.resource.jwt：项目启动过程中，检查到配置文件中有 security.oauth2.resource.jwt 的配置，就会生成 jwtTokenStore 的 bean，对令牌的校验就会使用 jwtTokenStore 。

# 验证

使用 postman 进行验证，获取 JWT 令牌

请求类型：post，

请求地址：http://localhost:8080/oauth/token?username=admin&password=123&grant_type=password

注意要添加客户端认证，在Authorizaiton栏中选择类型为 Basic Auth，设置用户明密码为 baby，123.

发送请求得到以下：

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDI4NjQzMjksInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9hZG1pbiJdLCJqdGkiOiJhNmI3NmVkZS0zZTVkLTRjZmYtYjQyMy05ODM0NTk2NDE3YWMiLCJjbGllbnRfaWQiOiJiYWJ5Iiwic2NvcGUiOlsiYWxsIl19.k86agYCZYx4ZwsxFObEcZigxs3wmgsoh_RlfwhiF5b4",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJhNmI3NmVkZS0zZTVkLTRjZmYtYjQyMy05ODM0NTk2NDE3YWMiLCJleHAiOjE2MDU0MTMxMjksImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtaW4iXSwianRpIjoiOGNlMGViYjktMWRiZi00Y2VhLWJkZjktMDVjNWY0N2ZhY2Q5IiwiY2xpZW50X2lkIjoiYmFieSJ9.ddlpMIyivTwh4LJHDRIFf_wlLGoq2OEzTyrmcA06bjE",
    "expires_in": 43199,
    "scope": "all",
    "jti": "a6b76ede-3e5d-4cff-b423-9834596417ac"
}
```

`access_token` 就是 JWT 令牌

然后请求 http://127.0.0.1:8081/admin/hello 进行权限验证，注意这里要在 Postman 的 Authorizaion 栏中选择 类型为 OAuth2.0 ，填入获取到的 `access_token` 





# 参考

* [Spring Boot Security 整合 OAuth2 设计安全API接口服务](https://mp.weixin.qq.com/s/0PAUErDh0qmcR4SUsTn15Q?spm=a2c6h.12873639.0.0.12604e52q584wa)

