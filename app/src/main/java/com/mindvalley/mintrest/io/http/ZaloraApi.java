package com.mindvalley.mintrest.io.http;

import com.mindvalley.mintrest.io.http.response.ProductListResponse;
import com.mindvalley.mintrest.io.http.response.WebResponse;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Dumb methods for using Zalora Api
 */
public interface ZaloraApi {


    @GET("/mobile-api/women/clothing")
    public Call<WebResponse<ProductListResponse>> getWomensClothing();
}
