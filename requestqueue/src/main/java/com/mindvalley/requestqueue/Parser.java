package com.mindvalley.requestqueue;

import com.mindvalley.requestqueue.Response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by rifat on 13/09/2015.
 */
public interface Parser<T> {

    T fromBody(InputStream res, Type type) throws IOException;

    Response toBody(T obj);

}
