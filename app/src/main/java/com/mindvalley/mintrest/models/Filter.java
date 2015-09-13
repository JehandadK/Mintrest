package com.mindvalley.mintrest.models;

import java.util.ArrayList;

public class Filter {

    private String id;
    private String label;
    private boolean multi;

    ArrayList<Option> options;

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isMulti() {
        return multi;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }
}
