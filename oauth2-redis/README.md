# oauth2 的令牌存入Redis





TokenStore 接口有多种实现类用来保存 access_token：

* JDBCTokenStore：令牌保存到数据库中
* InMemoryTokenStore：令牌保存到内存中
* RedisTokenStore：保存到Redis
* JwtTokenStore：使用jwt就是无状态登录，服务端不保存
* JwkTokenStore：保存到 JSON Web Key 中

本次基于 oauth2-jwt 项目更改，将 access_token 保存到Redis中。

添加 Redis 依赖并在 yaml 配置文件中配置 Redis：

```yaml
spring:
  redis:
    password: 654321
```

修改 TokenStore 实例：

```java
@Autowired
RedisConnectionFactory redisConnectionFactory;
@Bean
TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
}
```

之后使用 postman 请求接口：http://localhost:8080/oauth/token?username=admin&password=123&grant_type=password

之后就能在 Redis 中查看到存储的令牌信息：

access:1d251fd7-cfb2-4573-bcbb-2be5e4ba3380

access_to_refresh:1d251fd7-cfb2-4573-bcbb-2be5e4ba3380

...



# 客户端信息存入数据库

客户端信息入库的接口是 ClientDetailsService，该接口有两个实现类：

* InMemoryClientDetailsService
* JDBCClientDetailsService

JdbcClientDetailsService 会默认从 oauth_client_details 查询



pom.xml 中添加 MySQL 相关依赖，并配置数据库连接。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```



```yaml
  datasource:
    url: jdbc:mysql://localhost:3306/oauth2_store?useUnicode=true&characterEncoding=UTF-8
    username: root
    password: 654321

  # 允许我们自己的实例覆盖系统默认的实例，系统中已经创建 ClientDetailsService，我们使用自己创建的 ClientDetailsService
  main:
    allow-bean-definition-overriding: true
```

注意多了最后一项配置



提前准备好数据存入到 MySQL 中，首先创建 oauth_client_details 表：

```sql
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

插入数据：

```sql
INSERT INTO `oauth_client_details`
(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) 
VALUES 
('baby', 'res1', '123', 'all', 'password,refresh_token,authorization_code', NULL, 'ROLE_admin', NULL, NULL, NULL, 'true');

```



在 AuthorizationServer 提供自己的实例：

```java
    @Autowired
    DataSource dataSource;

    /*
      配置客户端信息存储在 MySQL 中
     */
    @Bean
    ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
 /**
     * 配置客户端详细信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService());
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore());
        services.setAccessTokenValiditySeconds(60 * 60 * 2);
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }

```

