package com.mindvalley.requestqueue;


import com.google.gson.reflect.TypeToken;
import com.mindvalley.requestqueue.impl.LruCache;
import com.mindvalley.requestqueue.impl.RealExecutor;
import com.mindvalley.requestqueue.impl.RealRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

/**
 * Can also be named as request factory, creates cancellable and executable requests.
 */
public class RequestFactory {
    private static final int CACHE_SIZE = 16 * 1024 * 1024;

    String endpoint;
    Parser parser;
    Executor executor;
    Cache cache;

    // TODO: Add request interceptor
    // TODO: Add default Headers using request interceptor
    // TODO: Ease of creating requests with json or xml bodies

    private RequestFactory(Parser parser, Executor executor, Cache cache, String endpoint) {
        this.endpoint = endpoint;
        this.parser = parser;
        this.executor = executor;
        this.cache = cache;
    }

    /**
     * If the url starts with complete http, endpoint is ignored.
     */
    public <T> Request<T> get(String url, Type type) {
        RealRequest<T> req = null;
        String completeUrl = url.matches("^http") ? url : endpoint + url;
        try {
            req = new RealRequest<T>(completeUrl, parser, executor, type );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return req;
    }


    public static class Builder {

        private Parser parser;
        private Executor executor = new RealExecutor();
        private Cache cache = new LruCache(CACHE_SIZE);
        private String endpoint;


        public Builder setParser(Parser parser) {
            this.parser = parser;
            return this;
        }

        public Builder setExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }

        public Builder setCache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Builder setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }


        public RequestFactory createApiClient() {
            return new RequestFactory( parser, executor, cache, endpoint);
        }
    }


}
