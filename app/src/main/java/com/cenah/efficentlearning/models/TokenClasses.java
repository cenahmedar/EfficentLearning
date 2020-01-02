package com.cenah.efficentlearning.models;

public class TokenClasses {
    private int takenClassroomUserId;
    private int givenClassroomId;
    private String givenClassroomDescription;
    private String courseName;
    private String courseProgrammingLanguage;


    public TokenClasses() {
    }

    public TokenClasses(int takenClassroomUserId, int givenClassroomId, String givenClassroomDescription, String courseName, String courseProgrammingLanguage) {
        this.takenClassroomUserId = takenClassroomUserId;
        this.givenClassroomId = givenClassroomId;
        this.givenClassroomDescription = givenClassroomDescription;
        this.courseName = courseName;
        this.courseProgrammingLanguage = courseProgrammingLanguage;
    }

    public int getTakenClassroomUserId() {
        return takenClassroomUserId;
    }

    public void setTakenClassroomUserId(int takenClassroomUserId) {
        this.takenClassroomUserId = takenClassroomUserId;
    }

    public int getGivenClassroomId() {
        return givenClassroomId;
    }

    public void setGivenClassroomId(int givenClassroomId) {
        this.givenClassroomId = givenClassroomId;
    }

    public String getGivenClassroomDescription() {
        return givenClassroomDescription;
    }

    public void setGivenClassroomDescription(String givenClassroomDescription) {
        this.givenClassroomDescription = givenClassroomDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseProgrammingLanguage() {
        return courseProgrammingLanguage;
    }

    public void setCourseProgrammingLanguage(String courseProgrammingLanguage) {
        this.courseProgrammingLanguage = courseProgrammingLanguage;
    }
}
