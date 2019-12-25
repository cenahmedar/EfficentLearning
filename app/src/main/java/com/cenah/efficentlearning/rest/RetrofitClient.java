package com.cenah.efficentlearning.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static Retrofit getUnsafeClient(String url){

        retrofit = new Retrofit.Builder()
                .baseUrl(url).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return retrofit;
    }
}
