package com.mindvalley.requestqueue.impl;

import android.os.Handler;

import com.mindvalley.requestqueue.Callback;
import com.mindvalley.requestqueue.Executor;
import com.mindvalley.requestqueue.Request;
import com.mindvalley.requestqueue.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealExecutor implements Executor {

    final int CORE_SIZE = 2;
    final int MAX_SIZE = 5; // TODO: Extract from system specs or take input from user
    private final Handler handler;

    ExecutorService service = Executors.newCachedThreadPool();

    HashMap<String, ArrayList<Callback>> requestQueue = new HashMap<>();

    public RealExecutor() {
        this.handler = new Handler(); // This handler is created on the main/ui thread
    }

    @Override
    public <T> void queue(final Request<T> request, final Callback<T> callback) {

        // Save callback and issue a resource id
        synchronized (requestQueue) {
            if (requestQueue.containsKey(request.getUrlHash())) {
                requestQueue.get(request.getUrlHash()).add(callback);
            } else {
                ArrayList<Callback> callbacks = new ArrayList<>();
                callbacks.add(callback);
                requestQueue.put(request.getUrlHash(), callbacks);
            }
        }

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response<T> res = request.execute();
                    postCallbacks(request.getUrlHash(), res);

                } catch (final IOException e) {
                    postErrorCallbacks(request.getUrlHash(), e);
                }

            }
        });
    }

    private <T> void postCallbacks(String urlHash, final Response<T> res) {
        synchronized (requestQueue) {
            for (final Callback<T> rC : requestQueue.get(urlHash)) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rC.onSuccess(res);
                    }
                });
            }
        }
    }

    private <T> void postErrorCallbacks(String urlHash, final Exception e) {
        synchronized (requestQueue) {
            for (final Callback<T> rC : requestQueue.get(urlHash)) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rC.onError(e);
                    }
                });
            }
        }
    }
}
