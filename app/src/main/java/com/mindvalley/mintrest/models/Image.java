package com.mindvalley.mintrest.models;

import com.google.gson.annotations.SerializedName;

/**
 *path: "http://static-origin.zalora.com.my/p/zalora-9825-231023-1-catalogmobile.jpg",
 format: "image/jpeg",
 width: "150",
 height: "218",
 default: true
 */
public class Image {


    private String path;
    private String format;

    private int width;
    private int height;

    @SerializedName("default")
    private boolean isDefault = false;

    public String getPath() {
        return path;
    }

    public String getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
