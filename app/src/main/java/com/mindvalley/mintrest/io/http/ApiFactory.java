package com.mindvalley.mintrest.io.http;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 */
public class ApiFactory {

    private static ZaloraApi defaultApi;

    public static ZaloraApi getApi() {
        if (defaultApi != null) return defaultApi;
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zalora.com.my").client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        defaultApi = retrofit.create(ZaloraApi.class);

        return defaultApi;

    }
}
