package com.demo.example.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.example.R;

/**
 * Created by hzsunyj on 2017/8/8.
 */

public class FadingView extends LinearLayout {

    private Paint paint;

    private int height;

    private int width;

    private TextView textView;

    public FadingView(Context context) {
        super(context);
        init(context, null);
    }

    public FadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT));
        LayoutInflater.from(context).inflate(R.layout.data_item_view, this);
        findViews();

    }

    private void findViews() {
        textView = (TextView) findViewById(R.id.text_view);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new LinearGradient(0, 0, 0, height, 0XD0000000, 0X10000000, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public void setAlpha(float alpha) {
        //        int argb = Color.argb((int) (alpha * 255), 0, 0, 0);
        //        paint.setShader(new LinearGradient(0, 0, 0, height, argb, Color.BLACK, Shader.TileMode.CLAMP));
        //        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        initPaint();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (height != 0) {
            canvas.drawRect(0, 0, width, height, paint);
        }
    }

    public TextView getTextView() {
        return textView;
    }
}
