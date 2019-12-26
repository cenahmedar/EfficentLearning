package com.cenah.efficentlearning.rest;


import com.cenah.efficentlearning.rest.services.StudentService;
import com.cenah.efficentlearning.rest.services.TeacherService;
import com.cenah.efficentlearning.rest.services.UserService;

public class RestFullHelper {

    private String BASE_URL;
    private final static String LOCAL_URL ="https://eflearning.azurewebsites.net/";

    public RestFullHelper() {
        this.BASE_URL = LOCAL_URL;
    }



    public UserService getUnsafeUserClient() {
        return RetrofitClient.getUnsafeClient(BASE_URL).create(UserService.class);
    }

    public StudentService getStudentClient() {
        return RetrofitClient.getUnsafeClient(BASE_URL).create(StudentService.class);
    }

    public TeacherService getTeacherClient() {
        return RetrofitClient.getUnsafeClient(BASE_URL).create(TeacherService.class);
    }
}
