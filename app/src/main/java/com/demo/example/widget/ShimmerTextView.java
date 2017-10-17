package com.demo.example.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.demo.example.R;

/**
 * Created by hzsunyj on 2017/9/26.
 */

public class ShimmerTextView extends android.support.v7.widget.AppCompatTextView {

    public static final int OFFSET_ONE_TIME = 15;

    private Paint paint;

    private LinearGradient gradient;

    private Matrix matrix;

    private int w, h;

    private boolean horizontal;

    private boolean autoStart;

    private int startColor;

    private int endColor;

    private static final int DEFAULT_START_COLOR = 0xFFFF50ED;

    private static final int DEFAULT_END_COLOR = 0xFF3455FF;

    private float offset = 0;

    private ValueAnimator animator;

    public ShimmerTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ShimmerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShimmerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = getPaint();
        matrix = new Matrix();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShimmerTextView);
        autoStart = array.getBoolean(R.styleable.ShimmerTextView_auto_start, false);
        startColor = array.getColor(R.styleable.ShimmerTextView_start_color, DEFAULT_START_COLOR);
        endColor = array.getColor(R.styleable.ShimmerTextView_end_color, DEFAULT_END_COLOR);
        horizontal = array.getBoolean(R.styleable.ShimmerTextView_direction, true);
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        setGradient();
    }

    private void setGradient() {
        if (horizontal) {
            gradient = new LinearGradient(0, 0, w, 0, new int[]{startColor, endColor, startColor}, new float[]{0,
                    0.5f, 1.0f}, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient(0, 0, 0, h, new int[]{startColor, endColor, startColor}, new float[]{0,
                    0.5f, 1.0f}, Shader.TileMode.CLAMP);
        }
        paint.setShader(gradient);
        invalidate();
        if (autoStart) {
            play();
        }
    }

    public void play() {
        ValueAnimator animator = getAnimator();
        if (animator.isRunning()) {
            return;
        }
        animator.start();
    }

    @NonNull
    private ValueAnimator getAnimator() {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setDuration(500);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offset += OFFSET_ONE_TIME;
                    if (horizontal) {
                        if (offset > w) {
                            offset = -w;
                        }
                    } else {
                        if (offset > h) {
                            offset -= h;
                        }
                    }
                    invalidate();
                }
            });
        }
        return animator;
    }

    public void stop() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public void reset() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        matrix.setTranslate(offset, 0);
        gradient.setLocalMatrix(matrix);
        super.onDraw(canvas);
    }
}
