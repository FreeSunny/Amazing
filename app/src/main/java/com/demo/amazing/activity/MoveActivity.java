package com.demo.amazing.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.demo.amazing.R;
import com.demo.amazing.util.LogUtil;

public class MoveActivity extends AppCompatActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, MoveActivity.class);
        context.startActivity(intent);
    }

    private FrameLayout layout;

    private float downX;

    private float downY;

    private float curX;

    private float curY;

    private int width;

    private int height;

    private int viewHeight;

    private int viewWidth;

    private boolean isAnimation;

    ObjectAnimator animator;

    Scroller scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        findView();
        setViewListener();
        initMetrics();
    }

    private void initMetrics() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        scroller = new Scroller(this);
    }

    private void findView() {
        layout = (FrameLayout) findViewById(R.id.move_view);
    }

    private void setViewListener() {
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isAnimation) {
                    return false;
                }
                //                LogUtil.e("onTouch=isAnimation=" + isAnimation);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getRawX();
                        downY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getRawX();
                        curY = event.getRawY();
                        onLayout();
                        downX = curX;
                        downY = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        onUp();
                        break;
                }
                return true;
            }
        });
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewWidth = layout.getWidth();
                viewHeight = layout.getHeight();
            }
        });
    }

    private void onUp() {
        final int offsetX;
        LogUtil.e("layout left=" + layout.getLeft() + " x=" + layout.getX());
        if (layout.getX() > (width - layout.getX() - viewWidth)) {
            offsetX = width - layout.getRight() - 10;
        } else {
            offsetX = 10 - layout.getLeft();
        }

        if (offsetX == 0) {
            return;
        }
        animator = ObjectAnimator.ofFloat(layout, "translationX", offsetX);
        animator.setDuration(100);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
                LogUtil.e("start left=" + layout.getLeft() + " offsetX=" + offsetX);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
                LogUtil.e("end left=" + layout.getLeft() + " offsetX=" + offsetX);
            }
        });
        animator.start();
    }

    private void onLayout() {
        int dx = (int) (curX - downX);
        int dy = (int) (curY - downY);

        if ((layout.getX() + dx) < 10) {
            dx = 0;
        } else if ((layout.getX() + viewWidth + dx) > width - 10) {
            dx = 0;
        }

        if ((layout.getY() + dy) < 10) {
            dy = 0;
        } else if ((layout.getY() + viewHeight + dy) > height - 200) {
            dy = 0;
        }
        layout.layout(layout.getLeft() + dx, layout.getTop() + dy, layout.getLeft() + viewWidth + dx, layout.getTop()
                + viewHeight + dy);
    }
}
