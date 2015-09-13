package com.mindvalley.requestqueue.impl;

import android.util.Log;

import com.mindvalley.requestqueue.Callback;
import com.mindvalley.requestqueue.Executor;
import com.mindvalley.requestqueue.Parser;
import com.mindvalley.requestqueue.Request;
import com.mindvalley.requestqueue.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 */
public class RealRequest<T> implements Request<T> {

    public static final int DEFAULT_TIMEOUT = 60 * 1000;
    public static final int READ_TIMEOUT = 60 * 1000;

    int method = Method.GET;
    URL url;
    Parser<T> parser;
    Executor executor;
    Reader body;
    Type type;

    T responseBody;

    private boolean isCancelled = false;

    public RealRequest(String url, Parser<T> parser, Executor executor, Type type) throws MalformedURLException {
        this(Method.GET, url, parser, executor, null, type);
    }

    /**
     * Used for methods types POST, PUT, DELETE etc. These requests cannot be cached as they are not idempotent
     *
     * @param method
     * @param url
     * @param parser
     * @param executor
     * @param body
     */
    public RealRequest(int method, String url, Parser<T> parser, Executor executor, Reader body, Type type) throws MalformedURLException {
        this.method = method;
        this.url = new URL(url);
        this.parser = parser;
        this.executor = executor;
        this.body = body;
        this.type = type;
    }

    public Response<T> execute() throws IOException {

        if (isCancelled) return null;

        HttpURLConnection conn = null;
        InputStream in = null;
        Response<T> res = new Response<>();
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(DEFAULT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            in = conn.getInputStream();
            responseBody = parser.fromBody(in, type);
            res.body = responseBody;

        } finally {
            if (conn != null) conn.disconnect();
        }

        return res;
    }

    public void queue(Callback<T> callback) {
        executor.queue(this, callback);
    }

    @Override
    public void cancel() {
        synchronized (this) {
            isCancelled = true;
        }
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
