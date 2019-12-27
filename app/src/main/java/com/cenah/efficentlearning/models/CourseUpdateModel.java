package com.cenah.efficentlearning.models;

public class CourseUpdateModel {
    private String name;
    private int programmingType;
    private int id;


    public CourseUpdateModel(String name, int programmingType, int id) {
        this.name = name;
        this.programmingType = programmingType;
        this.id = id;
    }
}
