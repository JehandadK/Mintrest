package com.mindvalley.requestqueue;

import java.io.IOException;

/**
 */
public interface Request<T> {

    String getUrlHash();

    Response<T> execute() throws IOException;

    void queue(Callback<T> callback);

    void cancel();

    boolean isCancelled();

    interface Method {
        int GET = 1;
        int POST = 2;
        // ...

    }

}
