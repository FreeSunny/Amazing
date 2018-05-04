package com.demo.amazing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.amazing.widget.MyGLSurfaceView;

public class OpenGLActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, OpenGLActivity.class);
        context.startActivity(intent);
    }

    MyGLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView = new MyGLSurfaceView(this);
        setContentView(surfaceView);
    }

}
