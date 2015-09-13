package com.mindvalley.requestqueue;

import java.io.IOException;

/**
 */
public interface Request<T> {

    interface Method {
        int GET = 1;
        int POST = 2;
        // ...

    }


    Response<T> execute() throws IOException;

    void queue(Callback<T> callback);

    void cancel();

    boolean isCancelled();

}
