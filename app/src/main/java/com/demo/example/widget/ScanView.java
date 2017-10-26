package com.demo.example.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;
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

	private Paint paint, paint1, paint2, paint3;

	private Bitmap mask, rect, leftScan, rightScan;

	private float ratio = 0.55f;

	private int upLeft, upTop;

	private int downLeft, downTop;

	private int faceW, faceH;

	private ValueAnimator animator;

	private int offset, startX, endX;

	public static final int OFFSET_ONE_TIME = 5;

	public static final int STAY_TIME = 25 * OFFSET_ONE_TIME;

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
		mask = BitmapFactory.decodeResource(context.getResources(), R.drawable.face_mask);
		rect = BitmapFactory.decodeResource(context.getResources(), R.drawable.face_rect);
		leftScan = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_left_scan);
		rightScan = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_right_scan);
		faceW = mask.getWidth();
		faceH = mask.getHeight();
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#4C0C0F3C"));
		paint1 = new Paint();
		paint1.setAntiAlias(true);
		paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		paint2 = new Paint();
		paint2.setAntiAlias(true);
		paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		paint3 = new Paint();
		paint3.setAntiAlias(true);
		paint3.setColor(Color.parseColor("#000000"));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		upLeft = width / 2 - mask.getWidth() / 2;
		upTop = (int) (height * ratio / 2 - mask.getHeight() / 2);

		downLeft = width / 2 - mask.getWidth() / 2;
		downTop = (int) (((height * (1 - ratio)) / 2 - mask.getHeight() / 2) + height * ratio);
		scan();
	}

	private void scan() {
		ValueAnimator animator = getAnimator();
		animator.start();
	}

	public void stop() {
		if (animator != null) {
			animator.cancel();
		}
	}

	boolean left2Right = true;

	@NonNull
	private ValueAnimator getAnimator() {
		if (animator == null) {
			animator = ValueAnimator.ofFloat(0.0f, 1.0f);
			animator.setDuration(3000);
			animator.setRepeatCount(ValueAnimator.INFINITE);
			animator.setRepeatMode(ValueAnimator.REVERSE);
			animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					if (left2Right) {
						offset += OFFSET_ONE_TIME;
						startX = 0;
						if (offset > faceW + STAY_TIME) {
							left2Right = false;
							endX = faceW;
						} else if (offset > faceW) {
							paint2.setAlpha(paint2.getAlpha() - 10);
							endX = faceW;
						} else {
							endX = offset < 0 ? 0 : offset;
							paint2.setAlpha(255);
						}
					} else {
						offset -= OFFSET_ONE_TIME;
						if (offset < -STAY_TIME) {
							left2Right = true;
							startX = 0;
						} else if (offset < 0) {
							startX = 0;
							paint2.setAlpha(paint2.getAlpha() - 10);
						} else {
							startX = offset > faceW ? faceW : offset;
							paint2.setAlpha(255);
						}
						endX = faceW;
					}
					invalidate();
				}
			});
		}
		return animator;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
		canvas.drawRect(0, 0, width, height, paint);

		canvas.drawBitmap(mask, upLeft, upTop, paint1);
		canvas.drawBitmap(mask, downLeft, downTop, paint1);
		canvas.restore();
		canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
		canvas.translate(upLeft, upTop);
		canvas.drawRect(startX, 0, endX, faceH, paint3);
		canvas.drawBitmap(left2Right ? leftScan : rightScan, 0, 0, paint2);
		canvas.translate(-upLeft, -upTop);
		canvas.restore();
		canvas.drawBitmap(rect, upLeft, upTop, null);
		canvas.drawBitmap(rect, downLeft, downTop, null);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stop();
	}
}
