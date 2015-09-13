package com.mindvalley.mintrest.io.http.response;

import com.google.gson.annotations.SerializedName;
import com.mindvalley.mintrest.models.Category;
import com.mindvalley.mintrest.models.Filter;
import com.mindvalley.mintrest.models.Product;

import java.util.ArrayList;

/**
 */
public class ProductListResponse {

    @SerializedName("product_count")
    int productCount;

    @SerializedName("category_ids")
    String categoryIds;

    ArrayList<Product> results;
    ArrayList<Category> categories;
    ArrayList<Filter> filters;

    public int getProductCount() {
        return productCount;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public ArrayList<Product> getResults() {
        return results;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }
}
