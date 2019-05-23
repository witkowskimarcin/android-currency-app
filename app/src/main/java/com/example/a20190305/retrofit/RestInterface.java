package com.example.a20190305.retrofit;

import com.example.a20190305.models.TableModel;
import com.example.a20190305.models.TableModelHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("/latest?")
    Call<TableModel> getTables(@Query("base") String currency);

    @GET("/latest?")
    Call<TableModel> getTables2(@Query("base") String currency, @Query("symbols") String c1, @Query("symbols") String c2);

    @GET("/history?")
    Call<TableModel> getTables3(@Query("start_at") String d1, @Query("ent_at") String d2);

    @GET("/history?")
    Call<TableModel> getTables4(@Query("start_at") String d1, @Query("end_at") String d2, @Query("base") String currency);

    @GET("/history?")
    Call<TableModelHistory> getTables5(@Query("start_at") String d1, @Query("end_at") String d2, @Query("base") String currency, @Query("symbols") String c);

    @GET("/history?")
    Call<TableModel> getTables6(@Query("start_at") String d1, @Query("end_at") String d2, @Query("base") String currency, @Query("symbols") String c1, @Query("symbols") String c2);
}
