package com.example.demo.model;

import java.io.Serializable;

/**
 * @author LiYingChun
 * @date 2019/5/12
 */
public class User implements Serializable {
    private static final long serialUID = 1L;
    private long id;
    private String userName;
    private String password;

    public User() {
        super();
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
