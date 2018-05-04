package com.demo.amazing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.demo.amazing.R;

/**
 * Created by hzsunyj on 2017/5/27.
 */

public class BitmapUtil {

    public static Bitmap rotateBitmap(Context context, Bitmap bitmap, int degree) {
        Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(dest);
        Matrix matrix = new Matrix();
        matrix.setRotate(degree, bitmap.getWidth() / 2, bitmap.getWidth() / 2);
        //matrix.setRotate(degree);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, matrix, paint);
        paint.setAntiAlias(true);
        paint.setTextSize(28);
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
        canvas.drawText("Image from", bitmap.getWidth() / 2, bitmap.getHeight() / 2, paint);
        return dest;
    }
}
