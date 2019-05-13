package com.example.a20190305.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rest {

    private static RestInterface serviceRest;
    private static OkHttpClient okHttpClient;
    private static Gson gson;

    private Rest(){}

    public static RestInterface getRest(){
        return serviceRest;
    }

    public static void init(){
        gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://api.exchangeratesapi.io/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        serviceRest = retrofit.create(RestInterface.class);
    }
}
