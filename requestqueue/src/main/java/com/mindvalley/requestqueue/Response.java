package com.mindvalley.requestqueue;

/**
 */
public class Response<T> {

    public T body;
    public int statusCode;
    private boolean isFromCache = false;

    public int getStatusCode() {
        return statusCode;
    }


    public T getBody() {
        return body;
    }


    public void setIsFromCache(boolean isFromCache) {
        this.isFromCache = isFromCache;
    }

    public boolean isFromCache(){
        return isFromCache;
    }
}
