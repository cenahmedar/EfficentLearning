package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.NotificaitonMeterial;
import com.cenah.efficentlearning.models.NotificationModel;
import com.cenah.efficentlearning.models.SuccessModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NotificationService {


    @GET("api/Common/GetNotifications")
    Call<ArrayList<NotificationModel>> GetNotifications(@Header("Authorization") String authHeader);


    @GET("/api/Material/GetFreshMaterials")
    Call<ArrayList<NotificaitonMeterial>> GetFreshMaterials(@Header("Authorization") String authHeader);

    @GET("/api/Common/GetUserSuccess")
    Call<SuccessModel> GetUserSuccess(@Header("Authorization") String authHeader);
}
