package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.ClassCreateModel;
import com.cenah.efficentlearning.models.ClassUpdateModel;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.models.JoinClassModel;
import com.cenah.efficentlearning.models.Student;
import com.cenah.efficentlearning.models.TokenClasses;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassService {

    @GET("api/GivenClassroom/GetAll")
    Call<ArrayList<Classes>> GetAll(@Header("Authorization") String authHeader);

    // for teacher
    @GET("api/GivenClassroom/GetClassrooms")
    Call<ArrayList<Classes>> GetClassrooms(@Header("Authorization") String authHeader);


    @GET("api/GivenClassroom/GetStudents/{givenClassroomId}")
    Call<ArrayList<Student>> GetStudentClass(@Header("Authorization") String authHeader, @Path(value ="givenClassroomId", encoded = true) int classRomId);


    @GET("api/GivenClassroom/FindAClassroom/{searchTerm}")
    Call<ArrayList<Classes>> Search(@Header("Authorization") String authHeader, @Path(value ="searchTerm", encoded = true) String search);


    @POST("api/GivenClassroom/Create")
    Call<ClassCreateModel> Post(@Header("Authorization") String authHeader, @Body ClassCreateModel createModel);


    @PUT("api/GivenClassroom/Update")
    Call<Classes> Update(@Header("Authorization") String authHeader, @Body ClassUpdateModel createModel);


    @DELETE("api/GivenClassroom/Delete/{givenClassroomId}")
    Call<Classes> Delete(@Header("Authorization") String authHeader,@Path(value ="givenClassroomId", encoded = true) int id);



    // for student

    @GET("api/TakenClassroom/GetClassrooms")
    Call<ArrayList<TokenClasses>> GetTakenClassroom(@Header("Authorization") String authHeader);



    @GET("api/TakenClassroom/GetJoinedCount")
    Call<Integer> GetJoinedCount(@Header("Authorization") String authHeader);


    @POST("api/TakenClassroom/Create")
    Call<ClassCreateModel> JoinClass(@Header("Authorization") String authHeader, @Body JoinClassModel createModel);

    @DELETE("api/TakenClassroom/Delete/{givenClassroomId}")
    Call<ClassCreateModel> DeleteJoin(@Header("Authorization") String authHeader,@Path(value ="givenClassroomId", encoded = true) int id);

}
