package com.mindvalley.mintrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Option {

    private String id;
    private String name;
    private String val;

    @SerializedName("products_count")
    private int productCount;

    ArrayList<Option> children;

    public ArrayList<Option> getChildren() {
        return children;
    }

    public int getProductCount() {
        return productCount;
    }

    public String getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
