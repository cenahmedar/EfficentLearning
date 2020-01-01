package com.cenah.efficentlearning.models;

public class PointModel {

    private int userId;
    private int materialId;
    private int score;

    public PointModel() {
    }

    public PointModel(int userId, int materialId, int score) {
        this.userId = userId;
        this.materialId = materialId;
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
