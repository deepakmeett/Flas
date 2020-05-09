package com.example.flas;
public class User {

    String email;
    String token;
    String uid;

    public User(String email, String token, String uid) {
        this.email = email;
        this.token = token;
        this.uid = uid;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
