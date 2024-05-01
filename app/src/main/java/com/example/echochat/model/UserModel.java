package com.example.echochat.model;

public class UserModel {
    String userName;
    String userId;
    String phone;

    public UserModel() {
    }

    public UserModel(String userName, String userId, String phone) {
        this.userName = userName;
        this.userId = userId;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
