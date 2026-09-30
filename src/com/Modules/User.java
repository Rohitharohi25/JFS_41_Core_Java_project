package com.Modules;

public class User {

    protected int userId;
    protected String username;
    protected String mailId;
    protected String password;

    public User() {
    }

    public User(int userId, String username, String mailId, String password) {
        this.userId = userId;
        this.username = username;
        this.mailId = mailId;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getMailId() {
        return mailId;
    }

    public String getPassword() {
        return password;
    }
}