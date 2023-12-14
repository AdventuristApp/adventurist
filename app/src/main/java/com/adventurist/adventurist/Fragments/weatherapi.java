package com.adventurist.adventurist.Fragments;

import com.adventurist.adventurist.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherapi {
    @GET("weather")
    Call<weather> getweather(@Query("q") String cityname,
                             @Query("appid") String apikey);
}
