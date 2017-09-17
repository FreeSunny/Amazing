package com.demo.example.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.example.R;

public class Move2Activity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, Move2Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move2);
    }
}
