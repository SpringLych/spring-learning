

整合MyBatis。

<!--more-->

## 使用IDEA构建

1. 打开IDEA，新建Spring Boot项目，依赖选择Web-web，SQL-MyBatis, SQL-JDBC,SQL-MySQL。

2. (暂时不用)更改`pom.xml`，添加PageHelper和mybatis generator依赖。在dependencies中太添加以下：

   ```xml
   <!-- 分页插件 -->
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper-spring-boot-starter</artifactId>
       <version>1.2.10</version>
   </dependency>
   <!-- alibaba的druid数据库连接池 -->
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid-spring-boot-starter</artifactId>
       <version>1.1.16</version>
   </dependency>
   ```

   在build-plugins中添加以下：

   ```xml
   <!-- mybatis generator 自动生成代码插件 -->
   <plugin>
       <groupId>org.mybatis.generator</groupId>
       <artifactId>mybatis-generator-maven-plugin</artifactId>
       <version>1.3.7</version>
       <configuration>
           <verbose>true</verbose>
           <overwrite>true</overwrite>
       </configuration>
   </plugin>
   ```

3. 在项目下新建包model，新建实体类`User.java`

4. mybatis相关文件配置：在`resources`文件夹下新建`mybatis`文件夹，在mybatis文件夹下新建`mybatis-config.xml`作为mybatis的配置文件；再新建mapper文件夹，存放mapper文件。

5. 使用更精简的yml替代properties配置文件：将`application.properties`更改为`application.yml`

   ```yaml
   server:
     port: 8080
   
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/springboot_mybatis?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false
       username: root
       password: ***REMOVED***
       driver-class-name: com.mysql.cj.jdbc.Driver
   
   mybatis:
     mapper-locations: classpath:mybatis/mapper/*.xml
     type-aliases-package: com.example.mybatis.model
     configuration:
       # 开启获取数据库自增主键
       use-generated-keys: true
       # 开启驼峰命名转换
       map-underscore-to-camel-case: true
   ```

6. 创建数据库

   ```mysql
   DROP TABLE IF EXISTS `users`;
   CREATE TABLE `users` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
     `userName` varchar(32) DEFAULT NULL COMMENT '用户名',
     `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
     `user_sex` varchar(32) DEFAULT NULL,
     `nick_name` varchar(32) DEFAULT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
   
   ```

## 使用XML

至此的配置都是xml版本的

### CRUD

创建model：/model/User.java

```java
public class User {
    private Long id;
    private String username;
    private String password;
    private String sex;
}
```

创建interface：/mapper/UserMapper.java

```java
public interface UserMapper {
    void insert(User user);

    void update(User user);

    void delete(Long id);

    User getById(Long id);

    List<User> getAll();
}
```

在 resources/mapper下创建 UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.example.mybatis.mapper.UserMapper">
    <!--    namespace xxMapper.java文件-->
    <sql id="Base_Column_List">
        id, username, password, sex
    </sql>

    <select id="getById" resultType="com.example.mybatis.model.User">
        SELECT
        <include refid="Base_Column_List"/>
        FROM users
        WHERE id = #{id}
    </select>

    <select id="getAll" resultType="com.example.mybatis.model.User">
        SELECT
        <include refid="Base_Column_List"/>
        FROM users
    </select>

    <insert id="insert" parameterType="com.example.mybatis.model.User">
        INSERT INTO user(username, password, sex)
        VALUES (#{username}, #{password}, #{sex})
    </insert>

    <update id="update" parameterType="com.example.mybatis.model.User">
        UPDATE users
        SET
        <if test="userName != null">username = #{username},</if>
        <if test="passWord != null">password = #{password},</if>
        WHERE
        id = #{id}
    </update>

    <delete id="delete" parameterType="long">
        DELETE
        FROM users
        WHERE id = #{id}
    </delete>
    
</mapper>
```

更改xxApplication，在项目入口添加`@MapperScan("com.example.mybatis.mapper")`，使其能够扫描到mapper。

### 测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        userMapper.insert(new User("aa", "pass1", "man"));
        userMapper.insert(new User("aa", "pass1", "man"));
        userMapper.insert(new User("aa", "pass1", "man"));
        assertEquals(3, userMapper.getAll().size());
    }
}
```



## 参考

* [CSDN - Spring boot Mybatis 整合（完整版）](<https://blog.csdn.net/winter_chen001/article/details/77249029>)
* [Github - spring-boot-examples](<https://github.com/ityouknow/spring-boot-examples>)