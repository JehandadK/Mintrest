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
public class LruCache<T> implements Cache<T> {


    private static final int MAX_NORMAL_CACHE_SIZE_IN_MB = 16;
    private static final int MAX_NORMAL_CACHE_SIZE = MAX_NORMAL_CACHE_SIZE_IN_MB * 1024 * 1024;

    private final int sizeLimit;

    private final AtomicInteger cacheSize;

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR = 1.1f;

    /**
     * Cache providing Least-Recently-Used logic
     */
    private final Map<String, T> lruCache = Collections.synchronizedMap(new LinkedHashMap<String, T>(INITIAL_CAPACITY, LOAD_FACTOR, true));


    /**
     * @param sizeLimit Maximum size for cache (in bytes)
     */
    public LruCache(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        cacheSize = new AtomicInteger();
        if (sizeLimit > MAX_NORMAL_CACHE_SIZE) {
            Log.w("Request Queue Library", "You set too large memory cache size (more than " + MAX_NORMAL_CACHE_SIZE_IN_MB + "Mb)");
        }
    }

    @Override
    public boolean put(String key, T value) {
        return false;
    }

    @Override
    public T get(String key) {
        return null;
    }

    @Override
    public T remove(String key) {
        return null;
    }

    @Override
    public Collection<String> keys() {
        return null;
    }

    @Override
    public void clear() {

    }

//    class LRU<K, V> extends LinkedHashMap<K, V> {
//
//        private static final long serialVersionUID = -6136127324434180097L;
//        public int capacity;
//        private boolean forceCollectBitmap;
//
//        public LRU(int capacity, boolean forceCollectBitmap) {
//            super(capacity + 1, 1.1f, true);
//            this.capacity = capacity;
//            this.forceCollectBitmap = forceCollectBitmap;
//        }
//
//        protected boolean removeEldestEntry(Entry eldest) {
//            //The SoftRefernce nullifies the Bitmap without calling recycle
//            //which creates a memory leak
//
//            if (size() > capacity && forceCollectBitmap) {
//                Bitmap elderBitmap = (Bitmap) eldest.getValue();
//                if (elderBitmap != null)
//                    elderBitmap.recycle();
//                elderBitmap = null;
//            }
//            return size() > capacity;
//        }
//
//        @Override
//        public void finalize() throws Throwable {
//
//            Collection<Bitmap> c = (Collection<Bitmap>) this.values();
//            // obtain an Iterator for Collection
//            Iterator<Bitmap> itr = c.iterator();
//            // iterate through HashMap values iterator
//            while (itr.hasNext()) {
//                ((Bitmap) itr.next()).recycle();
//            }
//            super.finalize();
//        }
//    }

//    @Override
//    public boolean put(String key, T value) {
//        boolean putSuccessfully = false;
//        // Try to add value to hard cache
//        int valueSize = getSize(value);
//        int sizeLimit = getSizeLimit();
//        int curCacheSize = cacheSize.get();
//        if (valueSize < sizeLimit) {
//            while (curCacheSize + valueSize > sizeLimit) {
//                T removedValue = removeNext();
//                if (lruCache.remove(removedValue)) {
//                    curCacheSize = cacheSize.addAndGet(-getSize(removedValue));
//                }
//            }
//            lruCache.put(key, value);
//            cacheSize.addAndGet(valueSize);
//
//            putSuccessfully = true;
//        }
//        return putSuccessfully;
//    }
//
//    @Override
//    public T remove(String key) {
//        T value = super.get(key);
//        if (value != null) {
//            if (hardCache.remove(value)) {
//                cacheSize.addAndGet(-getSize(value));
//            }
//        }
//        return super.remove(key);
//    }
//
//    @Override
//    public void clear() {
//        hardCache.clear();
//        cacheSize.set(0);
//    }
//
//    protected int getSizeLimit() {
//        return sizeLimit;
//    }
//
//    protected int getSize(T value) {
//        return 10;
//    }
//
////    @Override
////    protected int getSize(Bitmap value) {
////        return value.getRowBytes() * value.getHeight();
////    }
//
//    @Override
//    protected Bitmap removeNext() {
//        Bitmap mostLongUsedValue = null;
//        synchronized (lruCache) {
//            Iterator<Map.Entry<String, T>> it = lruCache.entrySet().iterator();
//            if (it.hasNext()) {
//                Map.Entry<String, T> entry = it.next();
//                mostLongUsedValue = entry.getValue();
//                it.remove();
//            }
//        }
//        return mostLongUsedValue;
//    }
//
//    @Override
//    protected Reference<T> createReference(T value) {
//        return new WeakReference<T>(value);
//    }

}
