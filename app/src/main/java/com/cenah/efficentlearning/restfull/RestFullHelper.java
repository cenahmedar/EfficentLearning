package com.cenah.efficentlearning.restfull;


import com.cenah.efficentlearning.restfull.services.ClassService;
import com.cenah.efficentlearning.restfull.services.CourseService;
import com.cenah.efficentlearning.restfull.services.StudentService;
import com.cenah.efficentlearning.restfull.services.TeacherService;
import com.cenah.efficentlearning.restfull.services.UserService;

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

    public CourseService getCourseClient() {
        return RetrofitClient.getUnsafeClient(BASE_URL).create(CourseService.class);
    }

    public ClassService getClassClient() {
        return RetrofitClient.getUnsafeClient(BASE_URL).create(ClassService.class);
    }

}
