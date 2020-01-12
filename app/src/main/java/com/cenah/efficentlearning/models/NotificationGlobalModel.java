package com.cenah.efficentlearning.models;

public class NotificationGlobalModel {

    private String date;
    private String message;

    private Integer id;
    private Integer givenClassroomId;
    private String description;
    private String deadline;
    private String creationTime;

    public NotificationGlobalModel(String date, String message, Integer id, Integer givenClassroomId, String description, String deadline, String creationTime) {
        this.date = date;
        this.message = message;
        this.id = id;
        this.givenClassroomId = givenClassroomId;
        this.description = description;
        this.deadline = deadline;
        this.creationTime = creationTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
