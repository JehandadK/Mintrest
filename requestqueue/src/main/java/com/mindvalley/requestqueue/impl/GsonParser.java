package com.mindvalley.requestqueue.impl;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.mindvalley.requestqueue.Parser;
import com.mindvalley.requestqueue.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by rifat on 13/09/2015.
 */
public class GsonParser<T> implements Parser<T> {

    private static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    private static final Charset UTF_8 = Charset.forName("UTF-8");


    private final Gson gson;

    public GsonParser(){
        this(new Gson());
    }

    public GsonParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public T fromBody(InputStream res ,Type type) {
        try {
            return gson.fromJson(new InputStreamReader(res, UTF_8), type);
        } finally {
            try {
                res.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public Response toBody(T obj) {
        return null;
    }
}
