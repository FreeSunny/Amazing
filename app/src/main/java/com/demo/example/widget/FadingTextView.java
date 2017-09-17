package com.demo.example.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

/**
 * Created by hzsunyj on 2017/8/8.
 */

public class FadingTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint paint;

    private int height;

    private int width;

    private RectF rectF;

    public FadingTextView(Context context) {
        super(context);
    }

    public FadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        initPaint();
        rectF = new RectF(0, 0, w, h);
    }

    public void setFading(float alpha) {
        if (alpha < 0) {
            alpha = 0.0f;
        } else if (alpha > 1) {
            alpha = 1.0f;
        }
        int endC = Color.argb((int) (alpha * 255), 0, 0, 0);
        paint.setShader(new LinearGradient(0, 0, 0, height, 0X00000000, endC, Shader.TileMode.CLAMP));
        setAlpha(alpha);
        invalidate();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setShader(new LinearGradient(0, 0, 0, height, 0X00000000, 0X00000000, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (height != 0) {
//            canvas.drawRoundRect(rectF, 30.0f, 30.0f, paint);
//        }
    }
}
