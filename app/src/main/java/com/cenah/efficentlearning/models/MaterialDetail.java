package com.cenah.efficentlearning.models;

public class MaterialDetail {
    private int materialType;
    private int materialScale;
    private String question;
    private String hint;
    private String description = null;
    private String deadline;
    private String creationTime;


    public MaterialDetail() {
    }

    public MaterialDetail(int materialType, int materialScale, String question, String hint, String description, String deadline, String creationTime) {
        this.materialType = materialType;
        this.materialScale = materialScale;
        this.question = question;
        this.hint = hint;
        this.description = description;
        this.deadline = deadline;
        this.creationTime = creationTime;
    }

    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public int getMaterialScale() {
        return materialScale;
    }

    public void setMaterialScale(int materialScale) {
        this.materialScale = materialScale;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
