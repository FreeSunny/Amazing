package com.demo.amazing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.demo.amazing.R;

/**
 * Created by hzsunyj on 2017/6/13.
 */

public class ScaleTextView extends View {

    private Paint paint;

    private int outColor;

    private int innerColor;

    private boolean drawBorder;

    private float scale;

    private String text;

    private int dimensionPixelSize;

    public ScaleTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ScaleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ScaleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleTextView);
        outColor = array.getColor(R.styleable.ScaleTextView_o_color, getResources().getColor(R.color.colorPrimary));
        innerColor = array.getColor(R.styleable.ScaleTextView_i_color, getResources().getColor(R.color.colorAccent));
        drawBorder = array.getBoolean(R.styleable.ScaleTextView_is_border, false);
        dimensionPixelSize = array.getDimensionPixelSize(R.styleable.ScaleTextView_text_size, getResources()
                .getDimensionPixelSize(R.dimen.inSize));
        array.recycle();
        outPaint();
    }

    private void outPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(dimensionPixelSize);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        setScaleX(scale);
        setScaleY(scale);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dimensionPixelSize * 4, dimensionPixelSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (drawBorder) {
            paint.setColor(outColor);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawText(text, 0, dimensionPixelSize, paint);
        }
        paint.setColor(innerColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(text, 0, dimensionPixelSize, paint);
    }
}
