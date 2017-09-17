package com.demo.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.example.R;
import com.demo.example.widget.FlowerGiftView;

public class AnimActivity extends AppCompatActivity implements View.OnClickListener {

    private FlowerGiftView flowerGiftView;

    public static void start(Context context) {
        Intent intent = new Intent(context, AnimActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        findViews();
    }

    private void findViews() {
        flowerGiftView = (FlowerGiftView) findViewById(R.id.right_gift_view);
        findViewById(R.id.record).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record:
                startA();
                break;
            case R.id.update:
                flowerGiftView.startAnim(7);
                break;
        }
    }

    private void startA() {
        flowerGiftView.startAnim(5);
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
