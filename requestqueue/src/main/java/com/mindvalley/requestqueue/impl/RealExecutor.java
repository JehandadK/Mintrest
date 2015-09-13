package com.mindvalley.requestqueue.impl;

import android.os.Handler;

import com.mindvalley.requestqueue.Callback;
import com.mindvalley.requestqueue.Executor;
import com.mindvalley.requestqueue.Request;
import com.mindvalley.requestqueue.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealExecutor implements Executor {

    final int CORE_SIZE = 2;
    final int MAX_SIZE = 5; // TODO: Extract from system specs or take input from user
    private final Handler handler;

    ExecutorService service = Executors.newCachedThreadPool();

    public RealExecutor() {
        this.handler = new Handler(); // This handler is created on the main/ui thread
    }

    @Override
    public <T> void queue(final Request<T> request, final Callback<T> callback) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response<T> res = request.execute();
                    //TODO: Store the result in cache
                    if (request.isCancelled())
                        return; // Do not call callbacks as the UI is probably invalidated;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(res);
                        }
                    });
                } catch (final IOException e) {

                    if (request.isCancelled())
                        return; // Do not call callbacks as the UI is probably invalidated;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }

            }
        });
    }
}
