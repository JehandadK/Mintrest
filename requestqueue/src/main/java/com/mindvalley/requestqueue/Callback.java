package com.mindvalley.requestqueue;

/**
 * Created by rifat on 13/09/2015.
 */
public interface Callback<T> {

    public void onSuccess(Response<T> response);

    public void onError(Throwable t);
}
