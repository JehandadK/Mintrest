package com.mindvalley.requestqueue.impl;

import com.mindvalley.requestqueue.Cache;
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
 * Not a complete implementation doesnot cover POST, PUT, DELETE. Serves to demonstrate concept.
 */
public class RealRequest<T> implements Request<T>, Callback<T> {

    public static final int DEFAULT_TIMEOUT = 60 * 1000;
    public static final int READ_TIMEOUT = 60 * 1000;

    int method = Method.GET;
    URL url;
    Parser<T> parser;
    Executor executor;
    Reader body;
    Type type;
    Cache cache;
    T responseBody;
    Callback<T> callback;

    private boolean isCancelled = false;

    public RealRequest(String url, Parser<T> parser, Executor executor, Type type, Cache cache) throws MalformedURLException {
        this(Method.GET, url, parser, executor, null, type, cache);
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
    public RealRequest(int method, String url, Parser<T> parser, Executor executor, Reader body, Type type, Cache cache) throws MalformedURLException {
        this.method = method;
        this.url = new URL(url);
        this.parser = parser;
        this.executor = executor;
        this.body = body;
        this.type = type;
        this.cache = cache;

    }

    @Override
    public String getUrlHash() {
        return url.getHost() + url.getPath();
    }

    public Response<T> execute() throws IOException {

        if (isCancelled) return null;

        Response<T> res = new Response<>();
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(DEFAULT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            in = conn.getInputStream();
            responseBody = parser.fromBody(in, type);
            synchronized (cache) {
                if (cache.get(url.getHost() + url.getPath()) != responseBody)
                    cache.put(url.getHost() + url.getPath(), responseBody);
            }
            res.body = responseBody;

        } finally {
            if (conn != null) conn.disconnect();
        }

        return res;
    }

    public void queue(Callback<T> callback) {
        Response<T> res = new Response<>();
        Object o = null;
        synchronized (cache) {
            o = cache.get(url.getHost() + url.getPath());// Due to underlying implementation
        }
        if (o != null) {
            res.setIsFromCache(true);
            res.body = (T) o;
            // This may look real messy but the alternative is to seperate requests implementations into ImageRequest and StringRequests as done in Volley
            callback.onSuccess(res);
            return;
        }
        this.callback = callback;
        executor.queue(this, this);
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

    @Override
    public void onSuccess(Response<T> response) {
        if (!isCancelled) callback.onSuccess(response);
    }

    @Override
    public void onError(Throwable t) {
        if (!isCancelled) callback.onError(t);
    }
}
