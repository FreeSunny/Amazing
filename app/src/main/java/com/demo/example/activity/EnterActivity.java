package com.demo.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.example.R;

public class EnterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        setViewListener();
    }

    private void setViewListener() {
        findViewById(R.id.go_to_main).setOnClickListener(this);
        findViewById(R.id.go_to_camera).setOnClickListener(this);
        findViewById(R.id.go_to_move).setOnClickListener(this);
        findViewById(R.id.go_to_move2).setOnClickListener(this);
        findViewById(R.id.go_to_anim).setOnClickListener(this);
        findViewById(R.id.go_to_fall).setOnClickListener(this);
        findViewById(R.id.go_to_fading).setOnClickListener(this);
        findViewById(R.id.go_to_bc).setOnClickListener(this);
        findViewById(R.id.go_to_lottie).setOnClickListener(this);
        findViewById(R.id.go_to_ws).setOnClickListener(this);
        findViewById(R.id.go_to_rd).setOnClickListener(this);
        findViewById(R.id.go_to_constraint).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_main:
                OpenGLActivity.start(this);
                break;
            case R.id.go_to_camera:
                CameraActivity.start(this);
                break;
            case R.id.go_to_move:
                MoveActivity.start(this);
                break;
            case R.id.go_to_move2:
                Move2Activity.start(this);
                break;
            case R.id.go_to_anim:
                AnimActivity.start(this);
                break;
            case R.id.go_to_fall:
                FallDownActivity.start(this);
                break;
            case R.id.go_to_fading:
                FadingActivity.start(this);
                break;
            case R.id.go_to_bc:
                BroadCastActivity.start(this);
                break;
            case R.id.go_to_lottie:
                LottieActivity.start(this);
                break;
            case R.id.go_to_ws:
                WSTestActivity.start(this);
                break;
            case R.id.go_to_rd:
                ScreenRecordActivity.start(this);
                break;
            case R.id.go_to_constraint:
                ConstraintActivity.start(this);
                break;
        }
    }
}
