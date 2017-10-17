package com.demo.example.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.example.R;

/**
 * Created by hzsunyj on 2017/9/26.
 */

public class ScanView extends View {

    int width;

    int height;

    private Paint paint;

    private Paint paint1;

    private Bitmap bitmap;

    private Bitmap bitmap1;

    private float ratio = 0.55f;

    private int upLeft, upTop;

    private int downLeft, downTop;

    public ScanView(Context context) {
        super(context);
        init(context, null);
    }

    public ScanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.face_mask);
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.face_rect);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#4C0C0F3C"));
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        upLeft = width / 2 - bitmap.getWidth() / 2;
        upTop = (int) (height * ratio / 2 - bitmap.getHeight() / 2);

        downLeft = width / 2 - bitmap.getWidth() / 2;
        downTop = (int) (((height * (1 - ratio)) / 2 - bitmap.getHeight() / 2) + height * ratio);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, width, height, paint);
        canvas.drawBitmap(bitmap, upLeft, upTop, paint1);
        canvas.drawBitmap(bitmap, downLeft, downTop, paint1);
        canvas.restore();
        canvas.drawBitmap(bitmap1, upLeft, upTop, paint);
        canvas.drawBitmap(bitmap1, downLeft, downTop, paint);
    }
}
