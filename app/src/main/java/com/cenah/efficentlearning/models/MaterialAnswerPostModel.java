package com.cenah.efficentlearning.models;

public class MaterialAnswerPostModel {
    private String answer;
    private int materialId;

    public MaterialAnswerPostModel() {
    }

    public MaterialAnswerPostModel(String answer, int materialId) {
        this.answer = answer;
        this.materialId = materialId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}
