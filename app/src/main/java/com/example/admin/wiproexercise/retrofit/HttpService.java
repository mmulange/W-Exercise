package com.example.admin.wiproexercise.retrofit;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpService {

    private OkHttpClient httpClient;

    public HttpService(File httpCacheDirectory) {
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (Exception e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
