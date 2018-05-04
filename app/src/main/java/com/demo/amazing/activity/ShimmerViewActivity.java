package com.demo.amazing.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.amazing.R;

public class ShimmerViewActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;

    TextView test1, test2, result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shimmer_view);
        findViews();
        setViewsListener();
    }

    private void findViews() {
        editText = findViewById(R.id.edit_text);
        test1 = findViewById(R.id.test1);
        test2 = findViewById(R.id.test2);
        result1 = findViewById(R.id.result1);
        result2 = findViewById(R.id.result2);
    }

    private void setViewsListener() {
        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test1:
                onTest1();
                break;
            case R.id.test2:
                onTest2();
                break;
        }
    }

    private void onTest1() {
        String s = editText.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }
        int times = Integer.valueOf(s);
        SetGet setGet = new SetGet();
        result1.setText(setGet.getSetTest(times));
    }

    private void onTest2() {
        String s = editText.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }
        int times = Integer.valueOf(s);
        SetGet setGet = new SetGet();
        result2.setText(setGet.directTest(times));
    }

    public class SetGet {
        public int a;
        private int b;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public String getSetTest(int times) {
            long begin = System.currentTimeMillis();
            for (int i = 0; i < times; ++i) {
                setB(getB() + 1);
            }
            long interval = System.currentTimeMillis() - begin;
            return "time=" + interval + " b=" + getB();
        }

        public String directTest(int times) {
            long begin = System.currentTimeMillis();
            for (int i = 0; i < times; ++i) {
                a = a + 1;
            }
            long interval = System.currentTimeMillis() - begin;
            return "time=" + interval + " a=" + a;
        }

    }
}
