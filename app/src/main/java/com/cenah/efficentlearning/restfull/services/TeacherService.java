package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.Teacher;
import com.cenah.efficentlearning.models.TeacherCreateModel;
import com.cenah.efficentlearning.models.TeacherUpdateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeacherService {

    @GET("api/User/GetAllTeachers")
    Call<ArrayList<Teacher>> GetAll(@Header("Authorization") String authHeader);

    @POST("api/User/CreateTeacher")
    Call<TeacherCreateModel> Post(@Header("Authorization") String authHeader, @Body TeacherCreateModel createModel);


    @DELETE("api/User/DeleteUser/{userId}")
    Call<Teacher> Delete(@Header("Authorization") String authHeader,@Path(value ="userId", encoded = true) int id);


    @PUT("api/User/UpdateUser")
    Call<Teacher> Update(@Header("Authorization") String authHeader,@Body TeacherUpdateModel object);

}
