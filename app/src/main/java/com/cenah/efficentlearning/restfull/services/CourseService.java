package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.Course;
import com.cenah.efficentlearning.models.CourseCreateModel;
import com.cenah.efficentlearning.models.ProgramingLanguagePup;
import com.cenah.efficentlearning.models.ProgramingType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CourseService {


    @GET("api/Course/GetAll")
    Call<ArrayList<Course>> GetAll(@Header("Authorization") String authHeader);

    @GET("api/Course/GetAllProgrammingTypes")
    Call<ArrayList<ProgramingType>> GetAllProgrammingTypes(@Header("Authorization") String authHeader);

    @POST("api/Course/Create")
    Call<Course> Post(@Header("Authorization") String authHeader,@Body CourseCreateModel createModel);

    @DELETE("api/Course/Delete/{courseId}")
    Call<Course> Delete(@Header("Authorization") String authHeader,@Path(value ="courseId", encoded = true) int id);


    @PUT("api/Course/Update")
    Call<Course> Update(@Header("Authorization") String authHeader,@Body Course course);

    @GET("api/Course/GetPopularityofProgrammingLanguages")
    Call<ArrayList<ProgramingLanguagePup>> GetPopularityofProgrammingLanguages(@Header("Authorization") String authHeader);


}
