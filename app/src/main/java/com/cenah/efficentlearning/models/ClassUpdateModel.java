package com.cenah.efficentlearning.models;

public class ClassUpdateModel {
    private int id;
    private int courseId;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClassUpdateModel(int id, int courseId, String description) {
        this.id = id;
        this.courseId = courseId;
        this.description = description;
    }
}
