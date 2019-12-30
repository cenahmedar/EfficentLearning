package com.cenah.efficentlearning.models;

public class NotificationModel {

    private int givenPracticeId;
    private int level;
    private String title;
    private String programmingType;

    public NotificationModel() {
    }

    public NotificationModel(int givenPracticeId, int level, String title, String programmingType) {
        this.givenPracticeId = givenPracticeId;
        this.level = level;
        this.title = title;
        this.programmingType = programmingType;
    }

    public int getGivenPracticeId() {
        return givenPracticeId;
    }

    public void setGivenPracticeId(int givenPracticeId) {
        this.givenPracticeId = givenPracticeId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProgrammingType() {
        return programmingType;
    }

    public void setProgrammingType(String programmingType) {
        this.programmingType = programmingType;
    }
}
