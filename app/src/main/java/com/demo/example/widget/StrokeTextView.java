package com.demo.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.demo.example.R;

import java.lang.reflect.Field;

/**
 * Created by hzsunyj on 2017/6/13.
 */

public class StrokeTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint paint;

    private int outColor;

    private int innerColor;

    private boolean drawBorder;

    private float scale;

    public StrokeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = this.getPaint();
        paint.setAntiAlias(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        outColor = array.getColor(R.styleable.StrokeTextView_out_color, getResources().getColor(R.color.yellow));
        innerColor = array.getColor(R.styleable.StrokeTextView_inner_color, getResources().getColor(R.color
                .colorAccent));
        drawBorder = array.getBoolean(R.styleable.StrokeTextView_draw_border, false);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawBorder) {
            // 描外层
            setTextColorUseReflection(outColor);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            super.onDraw(canvas);

            // 描内层，恢复原先的画笔
            setTextColorUseReflection(innerColor);
            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

        }
        super.onDraw(canvas);
    }

    /**
     * 使用反射的方法进行字体颜色的设置
     *
     * @param color
     */
    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        paint.setColor(color);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        setScaleX(scale);
        setScaleY(scale);
    }
}
