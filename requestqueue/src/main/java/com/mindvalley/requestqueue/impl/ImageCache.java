package com.mindvalley.requestqueue.impl;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.*;

import java.util.Collection;

/**
 * Created by rifat on 14/09/2015.
 */
public class ImageCache extends LruCache<Bitmap> {


    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the byte sizes of the entries in this cache.
     */
    public ImageCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        // Since the getBytes and getAllocationBytes have api compatibility issue.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return value.getAllocationByteCount();
        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        // Fix for android bitmap recycling issue
        if(!oldValue.isRecycled()) oldValue.recycle();
    }
}
