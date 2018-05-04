package com.demo.amazing.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.demo.amazing.R;
import com.demo.amazing.widget.FallDownView;

public class FallDownActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, FallDownActivity.class);
        context.startActivity(intent);
    }


    private FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_down);
        findViews();
    }

    private void findViews() {
        root = (FrameLayout) findViewById(R.id.root);
        final FallDownView child = new FallDownView(this);
        root.addView(child, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .MATCH_PARENT));
        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                child.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                child.start();
            }
        });
        child.postDelayed(new Runnable() {
            @Override
            public void run() {
                child.cancel();
            }
        }, 4000);
    }
}
