package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.Student;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface StudentService {

    @GET("api/User/GetAllStudents")
    Call<ArrayList<Student>> GetAllStudents(@Header("Authorization") String authHeader);



}
