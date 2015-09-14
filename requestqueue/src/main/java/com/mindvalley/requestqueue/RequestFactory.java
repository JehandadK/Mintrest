package com.mindvalley.requestqueue;


import com.google.gson.reflect.TypeToken;
import com.mindvalley.requestqueue.impl.LruCache;
import com.mindvalley.requestqueue.impl.RealExecutor;
import com.mindvalley.requestqueue.impl.RealRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

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

        String completeUrl = Pattern.compile("^http").matcher(url).find() ? url : endpoint + url;
        try {
            req = new RealRequest<T>(completeUrl, parser, executor, type, cache );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return req;
    }


    public static class Builder {

        private Parser parser;
        private Executor executor = new RealExecutor();
        private String endpoint;
        private Cache cache;


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
            if(executor == null || parser == null) throw new IllegalArgumentException();
            return new RequestFactory( parser, executor, cache, endpoint);
        }
    }


}
