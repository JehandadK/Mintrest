package com.mindvalley.mintrest.models;

import java.util.ArrayList;

public class Product {
    String id;
    ProductData data;
    ArrayList<Image> images;

    public String getId() {
        return id;
    }

    public ProductData getData() {
        return data;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public Image getDefaultImage() {
        for (Image img : images) {
            if (img.isDefault()) return img;
        }

        if (images.size() > 1) return images.get(0);
        // Not good
        return null;
    }
}
