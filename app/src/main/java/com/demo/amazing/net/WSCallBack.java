package com.demo.amazing.net;

/**
 * Created by hzsunyj on 2017/8/28.
 */

public interface WSCallBack {

    void isConnected();

    void onMessage(String text);

    void onFail(int code, String err);

    void close();
}
