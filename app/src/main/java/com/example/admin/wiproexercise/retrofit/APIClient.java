package com.example.admin.wiproexercise.retrofit;


import android.content.Context;

import com.example.admin.wiproexercise.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by  Maroti Mulange on 27-08-2018.
 */
public class APIClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIConstant.FEEDS_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
