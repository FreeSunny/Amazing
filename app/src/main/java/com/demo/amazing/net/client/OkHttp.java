package com.demo.amazing.net.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * global
 * Created by hzsunyj on 2017/8/28.
 */

public class OkHttp {

    /**
     * single instance
     */
    private static OkHttp ourInstance;

    /**
     * ok http client
     */
    private static OkHttpClient httpClient;

    /**
     * get instance
     *
     * @return
     */
    public static OkHttp getInstance() {
        if (ourInstance == null) {
            synchronized (Object.class) {
                if (ourInstance == null) {
                    ourInstance = new OkHttp();
                }
            }
        }
        return ourInstance;
    }

    /**
     * init
     */
    private OkHttp() {
        initClient();
    }

    /**
     *
     */
    private void initClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        httpClient = builder.build();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
