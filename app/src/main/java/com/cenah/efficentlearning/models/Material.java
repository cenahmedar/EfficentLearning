package com.cenah.efficentlearning.models;

public class Material {
    private int givenClassroomId;
    private String givenClassroom = null;
    private int materialType;
    private int materialScale;
    private String question;
    private String hint;
    private int announcementId;
    private String announcement = null;
    private String deadline;
    private String creationTime;
    private String materialAnswers = null;
    private int id;


    public Material() {
    }

    public Material(int givenClassroomId, String givenClassroom, int materialType, int materialScale, String question, String hint, int announcementId, String announcement, String deadline, String creationTime, String materialAnswers, int id) {
        this.givenClassroomId = givenClassroomId;
        this.givenClassroom = givenClassroom;
        this.materialType = materialType;
        this.materialScale = materialScale;
        this.question = question;
        this.hint = hint;
        this.announcementId = announcementId;
        this.announcement = announcement;
        this.deadline = deadline;
        this.creationTime = creationTime;
        this.materialAnswers = materialAnswers;
        this.id = id;
    }

    public int getGivenClassroomId() {
        return givenClassroomId;
    }

    public void setGivenClassroomId(int givenClassroomId) {
        this.givenClassroomId = givenClassroomId;
    }

    public String getGivenClassroom() {
        return givenClassroom;
    }

    public void setGivenClassroom(String givenClassroom) {
        this.givenClassroom = givenClassroom;
    }

    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public int getMaterialScale() {
        return materialScale;
    }

    public void setMaterialScale(int materialScale) {
        this.materialScale = materialScale;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
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

    public String getMaterialAnswers() {
        return materialAnswers;
    }

    public void setMaterialAnswers(String materialAnswers) {
        this.materialAnswers = materialAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
