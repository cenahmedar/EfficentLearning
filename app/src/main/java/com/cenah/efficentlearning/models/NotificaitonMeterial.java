package com.cenah.efficentlearning.models;

public class NotificaitonMeterial {

    private int id;
    private int givenClassroomId;
    private String description;
    private String deadline;
    private String creationTime;

    public NotificaitonMeterial() {
    }

    public NotificaitonMeterial(int id, int givenClassroomId, String description, String deadline, String creationTime) {
        this.id = id;
        this.givenClassroomId = givenClassroomId;
        this.description = description;
        this.deadline = deadline;
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGivenClassroomId() {
        return givenClassroomId;
    }

    public void setGivenClassroomId(int givenClassroomId) {
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
