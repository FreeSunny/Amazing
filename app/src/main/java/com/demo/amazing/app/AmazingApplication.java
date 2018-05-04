package com.demo.amazing.app;

import android.app.Application;

import com.demo.amazing.util.LogUtil;

/**
 * Created by hzsunyj on 2018/5/4.
 */
public class AmazingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.d("Application", "onTrimMemory=" + level);
    }
}
