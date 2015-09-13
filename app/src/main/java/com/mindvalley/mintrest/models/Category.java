package com.mindvalley.mintrest.models;

import java.util.ArrayList;

public class Category {
    String val;
    String name;
    String id;

    ArrayList<Category> children;

    public String getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Category> getChildren() {
        return children;
    }
}
