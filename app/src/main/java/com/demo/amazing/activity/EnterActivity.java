package com.demo.amazing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.amazing.R;
import com.demo.amazing.adpter.BaseAdapter;
import com.demo.amazing.adpter.EnterDelegate;
import com.demo.amazing.adpter.OnItemClickListener;
import com.demo.amazing.model.EnterData;

import java.util.ArrayList;

public class EnterActivity extends AppCompatActivity {

    private RecyclerView activityList;

    private ArrayList<EnterData> list;

    private void findViews() {
        activityList = findViewById(R.id.activity_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        findViews();
        initRV();
    }

    private void initRV() {
        preloadData();
        activityList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        activityList.setLayoutManager(new LinearLayoutManager(this));
        activityList.setAdapter(new BaseAdapter(list, new EnterDelegate(), new OnItemClickListener<EnterData>() {
            @Override
            public void onClick(View v, EnterData data) {
                onClickData(data);
            }

            @Override
            public boolean onLongClick(View v, EnterData data) {
                return false;
            }
        }));
    }

    private void onClickData(EnterData data) {
        Intent intent = new Intent(this, data.clazz);
        this.startActivity(intent);
    }

    private void preloadData() {
        list = new ArrayList<>();
        list.add(new EnterData("OpenGLActivity", OpenGLActivity.class));
        list.add(new EnterData("CameraActivity", CameraActivity.class));
        list.add(new EnterData("MoveActivity", MoveActivity.class));
        list.add(new EnterData("Move2Activity", Move2Activity.class));
        list.add(new EnterData("SendFlowersActivity", SendFlowersActivity.class));
        list.add(new EnterData("FallDownActivity", FallDownActivity.class));
        list.add(new EnterData("FadingActivity", FadingActivity.class));
        list.add(new EnterData("BroadCastActivity", BroadCastActivity.class));
        list.add(new EnterData("LottieActivity", LottieActivity.class));
        list.add(new EnterData("WSTestActivity", WSTestActivity.class));
        list.add(new EnterData("ScreenRecordActivity", ScreenRecordActivity.class));
        list.add(new EnterData("ConstraintActivity", ConstraintActivity.class));
        list.add(new EnterData("ScanFaceActivity", ScanFaceActivity.class));
        list.add(new EnterData("ShimmerViewActivity", ShimmerViewActivity.class));
        list.add(new EnterData("NestScrollingActivity", NestScrollingActivity.class));
        list.add(new EnterData("ScrollingActivity", ScrollingActivity.class));
        list.add(new EnterData("TabedActivity", TabedActivity.class));
        list.add(new EnterData("FragmentReplace", FragmentActivity.class));
    }

}
