package com.mindvalley.requestqueue;


import android.graphics.Bitmap;

import com.mindvalley.requestqueue.impl.ImageCache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ImageCacheTest {

    private static final Bitmap A = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);

    private static final Bitmap B = Bitmap.createBitmap(2, 2, Bitmap.Config.ALPHA_8);

    private static final Bitmap C = Bitmap.createBitmap(3, 3, Bitmap.Config.ALPHA_8);

    private ImageCache cache;

    @Before
    public void setUp() {
        cache = new ImageCache(13); // 14 Bytes
    }

    @After
    public void tearDown() {
        cache.clear();
        cache = null;
    }

    @Test
    public void testEnsureCorrectBitmapSize() {
        cache.put("1", A);
        assertEquals(cache.size(), 1); // 1 Bytes size of last bitmap of 1x1
        cache.put("2", B);
        assertEquals(cache.size(), 5); // 4 Bytes size of last bitmap of 2x2
        cache.put("2", B);
        assertEquals(cache.size(), 5); // Should Overwrite
    }

    @Test
    public void testEnsureMaxCacheSize() {
        cache.put("1", A);
        cache.put("2", B);
        cache.put("3", C);
        assertNull(cache.get("1")); // Should be evicted because we have only 13 bytes
        assertNotNull(cache.get("2"));
        assertNotNull(cache.get("3"));
    }

}