package com.mindvalley.mintrest.io.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mindvalley.requestqueue.RequestFactory;
import com.mindvalley.requestqueue.impl.GsonParser;
import com.mindvalley.requestqueue.impl.ImageCache;
import com.mindvalley.requestqueue.impl.ImageParser;
import com.mindvalley.requestqueue.impl.ObjectCache;
import com.mindvalley.requestqueue.impl.RealExecutor;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Singleton based on same library, this shows same library can be configured to use it as ImageRequests as well as JsonApiRequests.
 *
 *
 *
 */
public class ApiClientFactory {


    private static RequestFactory apiFactory = null;
    private static RequestFactory imgFactory = null;

    // Sharing the same executor so that its lightweight
    private static RealExecutor executor = new RealExecutor();

    // Cant Share the same caches, due to different types. Although same caches are possible they
    // will result in main thread been used to store byte data and parser and conversion type.
    // Hence we will use different caches, String Cache and Bitmap Cache.

    public static RequestFactory getDefaultJsonApiClient() {
        if (apiFactory != null) return apiFactory;


        Gson gson = defaultGson();
        GsonParser parser = new GsonParser(gson);
        //TODO: Show SimpleXML Parser can be added here
        apiFactory = new RequestFactory.Builder().setEndpoint("https://www.zalora.com.my/")
                .setParser(parser)
                .setExecutor(executor)
                .setCache(new ObjectCache(10))
                .createApiClient();
        return apiFactory;
    }

    public static RequestFactory getDefaultImageClient() {
        if (imgFactory != null) return imgFactory;
        imgFactory = new RequestFactory.Builder().setEndpoint("https://www.zalora.com.my/")
                .setParser(new ImageParser())
                .setCache(new ImageCache(8*1024*1024))
                .setExecutor(executor)
                .createApiClient();
        return imgFactory;
    }

    /**
     * Demonstrate extensible nature of parsing custom types like Date
     * @return
     */
    private static Gson defaultGson() {
        JsonSerializer<Date> dateSerializer = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        };

        return new GsonBuilder()
                .registerTypeAdapter(Date.class, dateSerializer)
                .registerTypeAdapter(Date.class, dateDeserializer).create();
    }


}
