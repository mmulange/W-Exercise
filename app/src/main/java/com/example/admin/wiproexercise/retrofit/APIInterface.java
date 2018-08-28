package com.example.admin.wiproexercise.retrofit;

import com.example.admin.wiproexercise.model.Feeds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface APIInterface {

    @Headers("content-type:application/json")
    @GET("2iodh4vg0eortkl/facts.json")
    Call<Feeds> getFeeds();
}
