package com.cenah.efficentlearning.models;

public class Course {

    private String name;
    private int programmingType;
    private String givenClassrooms ;
    private int id;

    @Override
    public String toString() {
        return name ;
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

    public String getGivenClassrooms() {
        return givenClassrooms;
    }

    public void setGivenClassrooms(String givenClassrooms) {
        this.givenClassrooms = givenClassrooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
