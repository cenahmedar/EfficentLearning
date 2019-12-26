package com.cenah.efficentlearning.rest.services;

import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.AuthBody;
import com.cenah.efficentlearning.models.Student;
import com.cenah.efficentlearning.models.User;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StudentService {

    @GET("api/User/GetAllStudents")
    Call<ArrayList<Student>> GetAllStudents(@Header("Authorization") String authHeader);



}
