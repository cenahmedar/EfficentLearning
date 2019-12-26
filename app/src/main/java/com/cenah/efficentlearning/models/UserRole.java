package com.cenah.efficentlearning.models;

public class UserRole {
    private float id;
    private String name;
    private String surname;
    private String userName;
    private String roleName;
    private String email;
    private boolean emailConfirmed;
    private String creationTime;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getEmail() {
        return email;
    }

    public boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public String getCreationTime() {
        return creationTime;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}