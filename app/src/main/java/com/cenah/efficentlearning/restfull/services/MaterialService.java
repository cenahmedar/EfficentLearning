package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.MaterialAnswer;
import com.cenah.efficentlearning.models.MaterialAnswerPostModel;
import com.cenah.efficentlearning.models.MaterialDetail;
import com.cenah.efficentlearning.models.MaterialPostModel;
import com.cenah.efficentlearning.models.MaterialType;
import com.cenah.efficentlearning.models.MaterialUpdateModel;
import com.cenah.efficentlearning.models.PointModel;
import com.cenah.efficentlearning.models.ScoreModel;
import com.cenah.efficentlearning.models.UserSuccess;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MaterialService {

    @GET("api/Material/GetMaterials/{givenClassroomId}")
    Call<ArrayList<Material>> GetMaterialsForClass(@Header("Authorization") String authHeader,
                                                   @Path(value ="givenClassroomId", encoded = true) int givenClassroomId);

    @POST("api/Material/Create")
    Call<MaterialPostModel> Post(@Header("Authorization") String authHeader,@Body MaterialPostModel object);

    @PUT("api/Material/Update")
    Call<Material> Update(@Header("Authorization") String authHeader,@Body MaterialUpdateModel object);


    @DELETE("api/Material/Delete/{materialId}")
    Call<Material> Delete(@Header("Authorization") String authHeader,@Path(value ="materialId", encoded = true) int id);


    @GET("api/Material/GetAllMaterialTypes")
    Call<ArrayList<MaterialType>> GetAllMaterialTypes(@Header("Authorization") String authHeader);


    @GET("/api/Material/GetMaterialDetail/{id}")
    Call<MaterialDetail> GetMaterialDetail(@Header("Authorization") String authHeader,
                                                      @Path(value ="id", encoded = true) int id);


    //answers


    @POST("api/MaterialAnswer/Create")
    Call<MaterialAnswerPostModel> PostAnswer(@Header("Authorizatio0n") String authHeader,
                                       @Body MaterialAnswerPostModel object);


    // for each user (student)
    @DELETE("api/MaterialAnswer/Delete/{materialId}")
    Call<MaterialAnswerPostModel> DeleteAnswer(@Header("Authorization") String authHeader,
                                               @Path(value ="materialId", encoded = true) int id);


    @GET("api/MaterialAnswer/GetMaterialAnswers/{materialId}")
    Call<ArrayList<MaterialAnswer>> GetMaterialAnswers(@Header("Authorization") String authHeader,
                                                       @Path(value ="materialId", encoded = true) int id);

    @PUT("api/MaterialAnswer/GivePoint")
    Call<PointModel> GivePoint(@Header("Authorization") String authHeader,@Body PointModel object);


    @GET("api/MaterialAnswer/GetScoreList/{givenClassroomId}")
    Call<ArrayList<ScoreModel>> GetScoreList(@Header("Authorization") String authHeader,
                                             @Path(value ="givenClassroomId", encoded = true) int id);


    @GET("api/MaterialAnswer/GetUserSuccess")
    Call<UserSuccess> GetUserSuccess(@Header("Authorization") String authHeader);

    // /api/MaterialAnswer/GetDoneMaterialCount   response int 1


}
