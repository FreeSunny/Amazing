package com.demo.amazing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.amazing.R;

/**
 * sticker battle progress bar
 * Created by hzsunyj on 2017/9/11.
 */

public class RtcScoreBar extends View {

    public static final int DEF_WIDTH = 6;

    /**
     * pk player 1 score
     */
    private int pk1Score;

    private int pk2Score;

    private int pk1Color;

    private int pk2Color;

    private int pk1Height;

    private int par_width;

    private int offset;

    private int viewW;

    private int viewH;

    private Paint paint;

    /**
     * overlap size
     */
    private int overSize;

    public RtcScoreBar(Context context) {
        super(context);
        init(context, null);
    }

    public RtcScoreBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RtcScoreBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RtcScoreBar);
        par_width = ta.getDimensionPixelSize(R.styleable.RtcScoreBar_width, DEF_WIDTH);
        pk1Color = ta.getColor(R.styleable.RtcScoreBar_pk1_color, getResources().getColor(R.color.rtc_pk1_color));
        pk2Color = ta.getColor(R.styleable.RtcScoreBar_pk2_color, getResources().getColor(R.color.rtc_pk2_color));
        overSize = ta.getDimensionPixelSize(R.styleable.RtcScoreBar_over_size, 1);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewW = w;
        viewH = h;
        offset = (viewW - par_width) / 2;
        pk1Height = (int) (viewH / 2.0 + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(pk1Color);
        canvas.drawRect(offset, overSize, par_width + offset, pk1Height, paint);
        paint.setColor(pk2Color);
        canvas.drawRect(offset, pk1Height, par_width + offset, viewH - overSize, paint);
    }

    /**
     * set pk score
     *
     * @param score
     */
    public void setPkScore(int score, boolean pk1) {
        if (pk1) {
            pk1Score += score;
        } else {
            pk2Score += score;
        }
        if (pk1Score == 0 || pk2Score == 0) {
            pk1Height = pk1Score == 0 ? 0 : viewH;
        } else {
            pk1Height = (int) (viewH * ((pk1Score * 1.0) / (pk1Score + pk2Score)) + 0.5f);
        }
        invalidate();
    }
}
