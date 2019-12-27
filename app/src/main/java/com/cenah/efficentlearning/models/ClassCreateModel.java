package com.cenah.efficentlearning.models;

public class ClassCreateModel {
    private int courseId;
    private String description;

    public ClassCreateModel(int courseId, String description) {
        this.courseId = courseId;
        this.description = description;
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
}
