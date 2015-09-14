package com.mindvalley.requestqueue;


import android.graphics.Bitmap;

import com.mindvalley.requestqueue.impl.ImageCache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class)
public class ImageCacheTest {

    private static final Bitmap A = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);

    private static final Bitmap B = Bitmap.createBitmap(2, 2, Bitmap.Config.ALPHA_8);

    private static final Bitmap C = Bitmap.createBitmap(3, 3, Bitmap.Config.ALPHA_8);

    private static final Bitmap D = Bitmap.createBitmap(4, 4, Bitmap.Config.ALPHA_8);

    private static final Bitmap E = Bitmap.createBitmap(5, 5, Bitmap.Config.ALPHA_8);

    private ImageCache cache;

    @Before
    public void setUp() {
        cache = new ImageCache(3* 1024); // 3 KBs
    }

    @After
    public void tearDown() {
        cache.clear();
        cache = null;
    }

    @Test
    public void getBitmapSize() {
        cache.put("1", A);
        assertEquals(cache.size(), 1);
        cache.put("2", B);
        assertEquals(cache.size(), 5);
        cache.put("2", B);
        assertEquals(cache.size(), 5);
//        assertThat(cache.getValueSize(B), is(4));
//        assertThat(cache.getValueSize(C), is(9));
//        assertThat(cache.getValueSize(D), is(16));
//        assertThat(cache.getValueSize(E), is(25));
    }

}