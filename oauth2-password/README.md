# oauth2-password模式

## 配置auth-server

主要是在授权码模式的基础上进行更改。

首先是在 auth-server 的 AuthorizationServer 中配置 password 模式：

```java
/**
     * 配置客户端详细信息，校验客户端，可以将信息存到数据库，本次存入内存
     * password模式，主要修改 authorizedGrantTypes 使之支持password模式
     */
@Override
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("baby")
        .secret(new BCryptPasswordEncoder().encode("123"))
        .resourceIds("res1")
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("all")
        .redirectUris("http://localhost:8082/index.html");
}
```

然后还是在 AuthorizationServer  中，注入 AuthenticationManager ，并进行配置：

```java
@Autowired
AuthenticationManager authenticationManager;
@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager)
        .tokenServices(tokenServices());
}
```

其中 **AuthenticationManager** 要在 **SecurityConfig** 中配置：

```java
@Bean
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
}
```



## 配置 client-app

index.html

```html
<body>
    你好

    <form action="/login" method="post">
        <table>
            <tr>
                <td>用户名：</td>
                <td><input name="username"></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" value="登录"></td>
            </tr>
        </table>
    </form>
    <h1 th:text="${msg}"></h1>
</body>
```







登录接口

```java

@PostMapping("/login")
public String login(String username, String password, Model model) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("username", username);
    map.add("password", password);
    map.add("client_secret", "123");
    map.add("client_id", "baby");
    map.add("grant_type", "password");
    Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
    System.out.println(resp);
    String access_token = resp.get("access_token");
    System.out.println(access_token);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + access_token);
    HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
    ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/admin/hello", HttpMethod.GET, httpEntity, String.class);
    model.addAttribute("msg", entity.getBody());
    return "index";
}
```





## 参考

[死磕 OAuth2，教练我要学全套的！](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488223&idx=1&sn=d1003f33ac5c866c88aa7542fcdf4992&chksm=e9c340bfdeb4c9a9758f38aff56488b241d31a3c1b44aab7a0cfe755489bae4f9e5a5dc1674f&scene=178#rd)

