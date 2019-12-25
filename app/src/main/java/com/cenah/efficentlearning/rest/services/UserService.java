package com.cenah.efficentlearning.rest.services;

import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.AuthBody;
import com.cenah.efficentlearning.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("api/Account/AccessToken")
    Call<Auth> Login(@Body AuthBody authBody);

    @GET("api/User/GetUserWithRole")
    Call<User> GetUserWithRole( @Header("Authorization") String authHeader);

    @POST("api/Account/Register")
    Call<User> Register(@Body User user, @Header("Authorization") String authHeader);

}