package com.mindvalley.requestqueue.impl;

import java.io.UnsupportedEncodingException;

/**
 * Created by rifat on 14/09/2015.
 */
public class ObjectCache extends LruCache<Object> {


    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the byte sizes of the entries in this cache.
     */
    public ObjectCache(int maxSize) {
        super(maxSize);
    }


}
