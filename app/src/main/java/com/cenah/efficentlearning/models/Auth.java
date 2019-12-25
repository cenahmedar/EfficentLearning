package com.cenah.efficentlearning.models;

public class Auth {

    public String token;
    public String refreshToken;
    public boolean success;
    public String messege ;

    public Auth(String token, String refreshToken, boolean success, String messege) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.success = success;
        this.messege = messege;
    }

    public Auth (){
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }
}
