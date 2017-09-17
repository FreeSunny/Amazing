package com.demo.example.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.example.R;

import java.util.Random;

/**
 * Created by hzsunyj on 2017/6/15.
 */

public class FallDownView extends View {

    public static final int DEF_FALL_COUNT = 50;

    public static final int DEF_FALL_VIEW_KIND = 5;

    private int count;

    private int viewKind;

    private DownView[] downViews;

    private Paint paint;

    private Random random;

    private Bitmap bitmaps[];

    private int offsetY = 4;

    private boolean finished;

    private int viewWidth;

    private int viewHeight;

    public FallDownView(Context context) {
        super(context);
        init(context, null);
    }

    public FallDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FallDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FallDownView);
        int resourceId = array.getResourceId(R.styleable.FallDownView_fall_down_image, R.drawable.rtc_icon_flower1);
        count = array.getInt(R.styleable.FallDownView_fall_down_count, DEF_FALL_COUNT);
        viewKind = array.getInt(R.styleable.FallDownView_fall_down_kind, DEF_FALL_VIEW_KIND);
        array.recycle();
        initBitmaps(resourceId);
    }

    /**
     * @param resourceId
     */
    private void initBitmaps(int resourceId) {
        random = new Random();
        bitmaps = new Bitmap[DEF_FALL_VIEW_KIND];
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        bitmaps[0] = bitmap;
        Matrix matrix = new Matrix();
        for (int i = 1; i < DEF_FALL_VIEW_KIND; ++i) {
            float scale = 1 + random.nextFloat();
            matrix.setScale(scale, scale);
            bitmaps[i] = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    public void start() {
        initPaint();
        downViews = new DownView[count];
        float speed;
        for (int i = 0; i < count; ++i) {
            speed = (1 + random.nextFloat()) * offsetY;
            downViews[i] = new DownView(random.nextInt(viewWidth), -random.nextInt(viewHeight), speed, bitmaps[i %
                    DEF_FALL_VIEW_KIND]);
        }
        finished = false;
        //invalidate();
        schedule(0);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (finished) {
            return;
        }
        DownView downView;
        for (int i = 0; i < count; ++i) {
            downView = downViews[i];
            if (downView.y + downView.bitmap.getHeight() < 0 || downView.y > viewHeight) {
                continue;
            }
            canvas.drawBitmap(downView.bitmap, downView.x, downView.y, paint);
        }
        adjustCoordinate();
    }

    public void cancel() {
        finished = true;
        invalidate();
    }

    private void adjustCoordinate() {
        int outCount = 0;
        DownView downView;
        for (int i = 0; i < count; ++i) {
            downView = downViews[i];
            downView.y += downView.speed;
            if (downView.y > viewHeight) {
                outCount++;
            } else if (downView.y > 0) {
                downView.x += downView.getOffset();
            }
        }
        if (outCount == count) {
            finished = true;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            if (!finished) {
                schedule(32);
            }
        }
    };

    private void schedule(int delay) {
        postDelayed(runnable, delay);
    }

    private class DownView {
        float x;
        float y;
        float speed;
        int angle;
        Bitmap bitmap;

        public DownView(float x, float y, float speed, Bitmap bitmap) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.bitmap = bitmap;
            angle = 0;
        }

        public int getOffset() {
            angle += 2;
            return Math.sin(Math.PI * angle / 180) > 0 ? 2 : -2;
        }
    }
}
