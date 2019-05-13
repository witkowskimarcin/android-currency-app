package com.example.a20190305.retrofit;

import com.example.a20190305.models.TableModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("/latest?")
    Call<TableModel> getTables(@Query("base") String currency);
}
