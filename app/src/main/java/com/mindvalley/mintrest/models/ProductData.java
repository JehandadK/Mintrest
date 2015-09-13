package com.mindvalley.mintrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Contains details of aproduct.
 */
public class ProductData {

    private String sku;
    private String name;

    @SerializedName("new-product")
    private boolean newProduct;

    private String url;

    private String brand;

    @SerializedName("max_price")
    private float maxPrice;

    private float price;

    private HashMap<String, Simple> simples;

    public HashMap<String, Simple> getSimples() {
        return simples;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public String getUrl() {
        return url;
    }

    public String getBrand() {
        return brand;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public float getPrice() {
        return price;
    }
}
