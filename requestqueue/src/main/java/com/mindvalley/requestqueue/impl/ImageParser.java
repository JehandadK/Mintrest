package com.mindvalley.requestqueue.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mindvalley.requestqueue.Parser;
import com.mindvalley.requestqueue.Response;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by rifat on 13/09/2015.
 */
public class ImageParser implements Parser<Bitmap> {


    @Override
    public Bitmap fromBody(InputStream res, Type type) throws IOException {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BufferedInputStream buffer = new BufferedInputStream(res);
        BitmapFactory.decodeStream(buffer, null, opts);
        buffer.reset();

        // Calculate inSampleSize
        // opts.inSampleSize = calculateInSampleSize(options, reqWidth,reqHeight);

        // Decode bitmap with inSampleSize set
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(buffer, null, opts);
    }

    @Override
    public Response toBody(Bitmap obj) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        obj.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        InputStream is = new ByteArrayInputStream(stream.toByteArray());
        return null;
    }
}
