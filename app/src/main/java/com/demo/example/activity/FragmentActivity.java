package com.demo.example.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.example.R;

public class FragmentActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        addFragment();
        handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchFragment();
            }
        }, 3000);
    }

    private void addFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new Fragment1());
        transaction.commitAllowingStateLoss();
    }

    private void switchFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new Fragment2());
        transaction.commitAllowingStateLoss();
    }

    @SuppressLint("ValidFragment")
    public static class Fragment1 extends Fragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setBackgroundColor(getResources().getColor(R.color.rtc_pk1_color));
            return textView;
        }
    }

    @SuppressLint("ValidFragment")
    public static class Fragment2 extends Fragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setBackgroundColor(getResources().getColor(R.color.rtc_pk2_color));
            return textView;
        }
    }
}
