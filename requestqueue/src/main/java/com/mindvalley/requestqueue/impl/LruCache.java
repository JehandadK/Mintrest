package com.mindvalley.requestqueue.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.mindvalley.requestqueue.Cache;
import com.mindvalley.requestqueue.Response;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void clear() {
        super.evictAll();
    }
}
