package com.cobacobms.finalproject.entity;

public class User {
    private String UserName;
    private String Mobile;
    private String Password;

    public User(String userName, String mobile, String password) {
        UserName = userName;
        Mobile = mobile;
        Password = password;
    }

    public User(String userName, String mobile) {
        UserName = userName;
        Mobile = mobile;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
