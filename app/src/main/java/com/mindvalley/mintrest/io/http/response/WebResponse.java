package com.mindvalley.mintrest.io.http.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 */
public class WebResponse<T> {

    boolean success;
    private Messages messages;
    private Session session;
    private T metadata;

    public boolean isSuccess() {
        return success;
    }

    public T getMetadata() {
        return metadata;
    }

    public class Session {
        public String id;
        public String expire;
        @SerializedName("YII_CSRF_TOKEN")
        public String yiiCSRFToken;
    }

    public class Messages {
        public ArrayList<String> success;
//      Probably a list of failure messages, but need api doc to confirm
//      public ArrayList<String> failure;

    }

}
