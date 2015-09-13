package com.mindvalley.requestqueue;

import com.mindvalley.requestqueue.impl.RealRequest;

import java.lang.reflect.Type;

/**
 * Executes requests on threads, and returns the result asynchrounusly.
 *
 * Allows to set no of threads
 * */
public interface Executor {


    <T> void queue(Request<T> request, Callback<T> callback);
}
