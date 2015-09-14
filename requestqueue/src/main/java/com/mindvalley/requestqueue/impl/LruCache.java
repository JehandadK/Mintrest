package com.mindvalley.requestqueue.impl;

import com.mindvalley.requestqueue.Cache;

/**
 * Limited cache. Provides object storing. Size of all stored response objects will not to exceed size limit (
 * {@link #getSizeLimit()}).<br />
 */
public abstract class LruCache<T> extends android.util.LruCache<String, T> implements Cache<T> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected T create(String key) {
        return super.create(key);
    }

    @Override
    public void clear() {
        super.evictAll();
    }
}
