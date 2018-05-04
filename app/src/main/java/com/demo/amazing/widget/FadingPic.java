package com.demo.amazing.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.amazing.R;

/**
 * Created by hzsunyj on 2017/8/9.
 */

public class FadingPic extends View {

    private Paint paint;

    private Paint paint1;

    private Bitmap bitmap;

    private Bitmap rotateBitmap;

    private int height;

    private int width;

    public FadingPic(Context context) {
        super(context);
        init(context, null);
    }

    public FadingPic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FadingPic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beautify);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setShader(new LinearGradient(0, 0, 0, height, 0x00000000, 0xff000000, Shader.TileMode.CLAMP));

        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        //matrix.setRotate(180); 不能形成镜像效果
        rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint1.setShader(new LinearGradient(0, height, 0, 2 * height, 0xff000000, 0x00000000, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2 * width, 2 * height);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawBitmap(bitmap, 0, 0, null);// 原始图片
        c.saveLayer(width, 0, width * 2, height, null, Canvas.ALL_SAVE_FLAG);
        c.drawBitmap(bitmap, width, 0, null);
        c.drawRect(width, 0, width * 2, height, paint);
        c.restore();
        // 倒影
        c.drawBitmap(rotateBitmap, 0, height, null);
        c.drawRect(0, height, width, 2 * height, paint1);
    }
}
