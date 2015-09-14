package com.mindvalley.mintrest.models;

import java.util.HashMap;

/**
 * Translated from Zalora API, these are meta properties and can vary in names and length, hence no objects
 * are created for this. They may or may not have submaps or sublists which can only be sure with a
 * written API Doc.
 */
public class Simple {

    private HashMap<String,String> meta;
    private HashMap<String,String> attributes;

    public HashMap<String, String> getMeta() {
        return meta;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }
}
