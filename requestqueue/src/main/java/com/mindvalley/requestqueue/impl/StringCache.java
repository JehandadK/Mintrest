package com.mindvalley.requestqueue.impl;

import android.graphics.Bitmap;
import android.os.Build;

import java.io.UnsupportedEncodingException;

/**
 * Created by rifat on 14/09/2015.
 */
public class StringCache extends LruCache<String> {


    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the byte sizes of the entries in this cache.
     */
    public StringCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, String value) {
        try {
            return value.getBytes("UTF-16BE").length;
        } catch (UnsupportedEncodingException e) {
            // Dosent happen
        }
        return 1;
    }

}
