package com.demo.amazing.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.demo.amazing.R;

/**
 * Created by hzsunyj on 2017/9/26.
 */

public class ScanFaceActivity extends Activity {

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ScanFaceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }
}
