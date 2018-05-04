package com.demo.amazing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by hzsunyj on 2017/8/9.
 */

public class FadingRecyclerView extends RecyclerView {

    private Paint paint;

    private int height;

    private int width;

    public FadingRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public FadingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FadingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setShader(new LinearGradient(0, 0, 0, 160, 0x00000000, 0xff000000, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }

    @Override
    public void draw(Canvas c) {
        c.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        super.draw(c);
        c.drawRect(0, 0, width, 160, paint);
        c.restore();
    }

}
