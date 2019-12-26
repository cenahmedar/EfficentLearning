package com.cenah.efficentlearning.restfull.services;

import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.AuthBody;
import com.cenah.efficentlearning.models.User;
import com.cenah.efficentlearning.models.UserRole;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("api/Account/AccessToken")
    Call<Auth> Login(@Body AuthBody authBody);

    @GET("api/User/GetUserWithRole")
    Call<UserRole> GetUserWithRole(@Header("Authorization") String authHeader);

    @POST("api/Account/Register")
    Call<User> Register(@Body User user);


}