package com.demo.amazing.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.amazing.R;

public class ConstraintActivity extends Activity {

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ConstraintActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen();
        setContentView(R.layout.activity_constraint);
    }

    private void fullscreen() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //        ActionBar actionBar = getActionBar();
        //        actionBar.hide();
    }
}
