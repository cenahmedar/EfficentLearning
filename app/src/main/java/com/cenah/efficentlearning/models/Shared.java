package com.cenah.efficentlearning.models;

public class Shared {

    private  UserRole userRole;

    private Auth auth;


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Shared(UserRole userRole, Auth auth) {
        this.userRole = userRole;
        this.auth = auth;
    }
}
