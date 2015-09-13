package com.mindvalley.requestqueue;

/**
 */
public class Response<T> {

    public T body;
    public int statusCode;

    public int getStatusCode() {
        return statusCode;
    }


    public T getBody() {
        return body;
    }



}
