package com.cenah.efficentlearning.models;

public class NotificationGlobalModel {

    private Integer givenPracticeId;
    private Integer level;
    private String title;
    private String programmingType;

    private Integer id;
    private Integer givenClassroomId;
    private String description;
    private String deadline;
    private String creationTime;

    public NotificationGlobalModel(Integer givenPracticeId, Integer level, String title, String programmingType, Integer id, Integer givenClassroomId, String description, String deadline, String creationTime) {
        this.givenPracticeId = givenPracticeId;
        this.level = level;
        this.title = title;
        this.programmingType = programmingType;
        this.id = id;
        this.givenClassroomId = givenClassroomId;
        this.description = description;
        this.deadline = deadline;
        this.creationTime = creationTime;
    }

    public Integer getGivenPracticeId() {
        return givenPracticeId;
    }

    public void setGivenPracticeId(Integer givenPracticeId) {
        this.givenPracticeId = givenPracticeId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGivenClassroomId() {
        return givenClassroomId;
    }

    public void setGivenClassroomId(Integer givenClassroomId) {
        this.givenClassroomId = givenClassroomId;
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
