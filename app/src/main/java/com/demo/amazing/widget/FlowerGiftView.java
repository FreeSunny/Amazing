package com.demo.amazing.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.amazing.R;

/**
 * Created by hzsunyj on 2017/6/13.
 */

public class FlowerGiftView extends RelativeLayout {

    /**
     * 动画执行状态
     */
    private boolean isAnimation;

    /**
     * 是否从左到右
     */
    private boolean left2Right;

    /**
     * gift desc
     */
    private String desc;

    /**
     * 设置文案描述
     */
    private TextView giftDesc;

    /**
     * 缩放动画
     */
    private StrokeTextView giftCount;

    /**
     * 显示次数变动
     */
    private int incTimes = 0;

    /**
     * x方向移动位置
     */
    private int transX;

    /**
     * y方向移动位置
     */
    private int transY;

    private ObjectAnimator scale;

    private AnimatorSet set;

    public FlowerGiftView(Context context) {
        super(context);
        init(context, null);
    }

    public FlowerGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlowerGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowerGiftView);
        left2Right = array.getBoolean(R.styleable.FlowerGiftView_left_to_right, false);
        desc = array.getString(R.styleable.FlowerGiftView_gift_desc);
        array.recycle();
        View inflate = inflate(getContext(), R.layout.gift_view_layout, this);
        findViews(inflate);
    }

    private void findViews(View view) {
        giftDesc = (TextView) view.findViewById(R.id.gift_desc);
        giftDesc.setText(desc);
        giftCount = (StrokeTextView) view.findViewById(R.id.gift_count);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        transX = this.getMeasuredWidth();
        transY = this.getMeasuredHeight();
    }

    /**
     * 如果动画不存在，先创建再执行
     * 如果正在运行，则直接更新执行次数
     * 如果没执行， 则启动执行
     * 当动画执行完成后，判断RepeatCount==incTimes，相等表示执行完成了。否则执行剩下的值
     *
     * @param count
     */
    public void startAnim(int count) {
        if (set == null) {
            set = new AnimatorSet();
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.playSequentially(getTrans(), getScale(count), getFlyFade());
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAnimation = false;
                    FlowerGiftView.this.setVisibility(INVISIBLE);
                    animate().alpha(1).translationY(0).start();
                    int remainTimes = scale.getRepeatCount() - incTimes;
                    if (remainTimes > 0) {
                        startAnim(remainTimes);
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isAnimation = true;
                    FlowerGiftView.this.setVisibility(VISIBLE);
                    giftCount.setText("x 1 ");
                    incTimes = 1;
                }
            });
            set.start();
        } else {
            if (isAnimation) {
                scale.setRepeatCount(scale.getRepeatCount() + count);
            } else {
                scale.setRepeatCount(count - 1);
                set.start();
            }
        }
    }

    @NonNull
    private ObjectAnimator getFlyFade() {
        PropertyValuesHolder flyUp = PropertyValuesHolder.ofFloat("translationY", 0, left2Right ? -transY : transY);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        return ObjectAnimator.ofPropertyValuesHolder(this, flyUp, alpha).setDuration(500);
    }

    private ObjectAnimator getScale(int count) {
        scale = ObjectAnimator.ofFloat(giftCount, "scale", 1.0f, 2.0f, 1.0f);
        scale.setDuration(500);
        scale.setRepeatCount(count - 1);
        scale.setRepeatMode(ObjectAnimator.RESTART);
        scale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                giftCount.setText("x " + (++incTimes) + " ");
            }
        });
        return scale;
    }

    @NonNull
    private ObjectAnimator getTrans() {
        ObjectAnimator trans = ObjectAnimator.ofFloat(this, "translationX", left2Right ? -transX : transX, 0);
        trans.setDuration(300);
        return trans;
    }
}
