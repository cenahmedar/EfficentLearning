package com.cenah.efficentlearning.models;

public class CourseCreateModel {
    private String name;
    private int programmingType;

    public CourseCreateModel(String name, int programmingType) {
        this.name = name;
        this.programmingType = programmingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgrammingType() {
        return programmingType;
    }

    public void setProgrammingType(int programmingType) {
        this.programmingType = programmingType;
    }
}
