package com.cenah.efficentlearning.models;

public class NotificationModel {


    private String message;
    private String date;

    public NotificationModel(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public NotificationModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
