package com.mindvalley.requestqueue;

import java.util.Collection;

/**
 * Created by rifat on 13/09/2015.
 */
public interface Cache<T> {

    /**
     * Puts value into cache by key
     *
     * @return <b>true</b> - if value was put into cache successfully, <b>false</b> - if value was <b>not</b> put into
     * cache
     */
    boolean put(String key, T value);

    /** Returns value by key. If there is no value for key then null will be returned. */
    T get(String key);

    /** Removes item by key */
    T remove(String key);

    /** Returns all keys of cache */
    Collection<String> keys();

    /** Remove all items from cache */
    void clear();
}
