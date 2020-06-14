package com.example.flas;
public class Token {

    private String email;
    private String token;
    private String uid;

    public Token(String email, String token, String uid) {
        this.email = email;
        this.token = token;
        this.uid = uid;
    }

    public Token(String token) {
        this.token = token;
    }

    public Token() {
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
