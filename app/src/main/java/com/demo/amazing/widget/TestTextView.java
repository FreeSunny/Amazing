package com.demo.amazing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.demo.amazing.R;
import com.demo.amazing.util.LogUtil;

/**
 * Created by hzsunyj on 2017/11/27.
 */

public class TestTextView extends AppCompatTextView {

	private String tag;

	public TestTextView(Context context) {
		super(context);
		init(context, null);
	}

	public TestTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TestTextView);
		tag = array.getString(R.styleable.TestTextView_tag_name);
		array.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		LogUtil.e("tag" + tag + " widthMeasureSpec=" + widthMeasureSpec + " mode=" + mode + " size=" + size);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		LogUtil.e("tag" + tag + " width=" + getMeasuredWidth() + " w=" + getWidth());
	}
}
