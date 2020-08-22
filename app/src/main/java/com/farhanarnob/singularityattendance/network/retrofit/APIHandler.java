package com.farhanarnob.singularityattendance.network.retrofit;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIHandler<T> {
    public static final long CONNECTION_TIME_OUT = 5;
    private static final String DEVELOP_API_URL = "http://128.199.215.102:4040";
    private static final String STAGE_API_URL = "http://128.199.215.102:4040";
    private static final String PRODUCTION_API_URL = "http://128.199.215.102:4040";
    public static final String API_URL = PRODUCTION_API_URL;
    private static Retrofit restAdapter;

    public T getApiInterface(Class<T> apiClass) {
        T apiInterface = null;
        try {
            if (restAdapter == null) {
                restAdapter = getRestAdapter();
            }
            apiInterface = restAdapter.create(apiClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiInterface;
    }

    private static Retrofit getRestAdapter() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        if (restAdapter == null) {
            restAdapter = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getClient())
                    .build();
        }
        return restAdapter;
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        okClientBuilder.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES);
        okClientBuilder.readTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES);
        okClientBuilder.writeTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES);
        return okClientBuilder.build();

    }
}
