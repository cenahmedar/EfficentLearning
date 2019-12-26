package com.cenah.efficentlearning.models;

public class Student {
    private String name;
    private String surname;
    private String creationTime;
    private String takenClassrooms = null;
    private String givenClassrooms = null;
    private String donePractices = null;
    private String refreshTokens = null;
    private String comments = null;
    private String materialAnswers = null;
    private float id;
    private String userName;
    private String normalizedUserName;
    private String email;
    private String normalizedEmail;
    private boolean emailConfirmed;
    private String passwordHash;
    private String securityStamp;
    private String concurrencyStamp;
    private String phoneNumber = null;
    private boolean phoneNumberConfirmed;
    private boolean twoFactorEnabled;
    private String lockoutEnd = null;
    private boolean lockoutEnabled;
    private float accessFailedCount;


    // Getter Methods

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getTakenClassrooms() {
        return takenClassrooms;
    }

    public String getGivenClassrooms() {
        return givenClassrooms;
    }

    public String getDonePractices() {
        return donePractices;
    }

    public String getRefreshTokens() {
        return refreshTokens;
    }

    public String getComments() {
        return comments;
    }

    public String getMaterialAnswers() {
        return materialAnswers;
    }

    public float getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getNormalizedUserName() {
        return normalizedUserName;
    }

    public String getEmail() {
        return email;
    }

    public String getNormalizedEmail() {
        return normalizedEmail;
    }

    public boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public String getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public String getLockoutEnd() {
        return lockoutEnd;
    }

    public boolean getLockoutEnabled() {
        return lockoutEnabled;
    }

    public float getAccessFailedCount() {
        return accessFailedCount;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setTakenClassrooms(String takenClassrooms) {
        this.takenClassrooms = takenClassrooms;
    }

    public void setGivenClassrooms(String givenClassrooms) {
        this.givenClassrooms = givenClassrooms;
    }

    public void setDonePractices(String donePractices) {
        this.donePractices = donePractices;
    }

    public void setRefreshTokens(String refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setMaterialAnswers(String materialAnswers) {
        this.materialAnswers = materialAnswers;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNormalizedUserName(String normalizedUserName) {
        this.normalizedUserName = normalizedUserName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNormalizedEmail(String normalizedEmail) {
        this.normalizedEmail = normalizedEmail;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSecurityStamp(String securityStamp) {
        this.securityStamp = securityStamp;
    }

    public void setConcurrencyStamp(String concurrencyStamp) {
        this.concurrencyStamp = concurrencyStamp;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumberConfirmed(boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public void setLockoutEnd(String lockoutEnd) {
        this.lockoutEnd = lockoutEnd;
    }

    public void setLockoutEnabled(boolean lockoutEnabled) {
        this.lockoutEnabled = lockoutEnabled;
    }

    public void setAccessFailedCount(float accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }
}
