package com.cenah.efficentlearning.models;

public class MaterialAnswer {
    private int materialId;
    private String answer;
    private String creationTime;
    private int userId;
    private String name;
    private String surname;
    private String userName;
    private int score;

    public MaterialAnswer() {
    }

    public MaterialAnswer(int materialId, String answer, String creationTime, int userId, String name, String surname, String userName, int score) {
        this.materialId = materialId;
        this.answer = answer;
        this.creationTime = creationTime;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.score = score;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
