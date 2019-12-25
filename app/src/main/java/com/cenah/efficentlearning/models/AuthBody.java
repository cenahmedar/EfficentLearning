package com.cenah.efficentlearning.models;

public class AuthBody {

    public String email;

    public String password;

    public AuthBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
