package com.cenah.efficentlearning.models;

public class MaterialUpdateModel {
    private int id;
    private int materialScale;
    private String question;
    private String hint;
    private String description;

    public MaterialUpdateModel(int id, int materialScale, String question, String hint, String description) {
        this.id = id;
        this.materialScale = materialScale;
        this.question = question;
        this.hint = hint;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
