package com.cenah.efficentlearning.models;

public class Announcement {
    private int id;
    private int materialId;
    private String description;
    private String creationTime;

    public Announcement() {
    }

    public Announcement(int id, int materialId, String description, String creationTime) {
        this.id = id;
        this.materialId = materialId;
        this.description = description;
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
