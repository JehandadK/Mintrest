package com.mindvalley.mintrest.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rifat on 12/09/2015.
 */
public abstract class ListItemBinder<T> {

    private List<T> items;
    private int     position;


    protected ListItemBinder(List<T> items) {
        this.items = new ArrayList<T>( items );
    }

    public int getSize () {
        return items.size();
    }

    public T getItem (int position) {
        return items.get( position );
    }

    final View createView (LayoutInflater inflater) {
        return inflater.inflate( getLayoutId(), null );
    }

    protected abstract int getLayoutId ();

    protected abstract BaseHolder createHolder (View view);

    protected void decorateView (Context ctx, BaseHolder holder, View view, T item) {
    }

    protected abstract void setData (Context ctx, BaseHolder holder, T item);

    final public void setItems (List<T> newItems) {
        items.clear();
        items.addAll( newItems );
    }

    public void addItem (T newItem) {
        this.items.add( newItem );
    }

    public final void addItems (List<T> items) {
        if ( this.items.isEmpty() ) this.items = new ArrayList<T>();
        this.items.addAll( items );
    }

    public final void clearItems () {
        items.clear();
    }

    public void setPosition (int position) {
        this.position = position;
    }

    public int getPosition () {
        return position;
    }
}