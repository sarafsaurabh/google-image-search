package com.self.googleimagesearch;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Created by ssaraf on 9/27/15.
 */
public class RoundedTransformation implements
        com.squareup.picasso.Transformation {
    private final int radius;
    private final int margin; // dp

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundedTransformation(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(),
                source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(margin, margin, source.getWidth()
                - margin, source.getHeight() - margin), radius, radius, paint);
        if (source != output) {
            source.recycle();
        }

        //draw rectangles over the corners we want to be square
            canvas.drawRect(0, source.getHeight()/2,
                    source.getWidth()/2, source.getHeight(), paint);
            canvas.drawRect(source.getWidth()/2,
                    source.getHeight()/2, source.getWidth(), source.getHeight(), paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(input, 0, 0, paint);

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
