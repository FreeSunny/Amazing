package com.demo.amazing.net.config;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class LinkStatus {

    public static final int BROKEN = 0;

    public static final int CONNECTTING = 1;

    public static final int CONNECTED = 2;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isBroken() {
        return status == BROKEN;
    }

    public boolean isConnected() {
        return status == CONNECTED;
    }
}
