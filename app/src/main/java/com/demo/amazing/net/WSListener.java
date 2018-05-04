package com.demo.amazing.net;

import com.demo.amazing.net.config.WSErrorCode;
import com.demo.amazing.util.LogUtil;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by hzsunyj on 2017/8/28.
 */

public class WSListener extends WebSocketListener {

    WSCallBack callBack;

    public WSListener(WSCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        callBack.isConnected();
        LogUtil.e("client onOpen");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        callBack.onMessage(text);
        LogUtil.e("client onMessage=" + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        LogUtil.e("client onClosed: code=" + code + " reason=" + reason);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        LogUtil.e("client onFailure: t=" + t.getMessage());
        callBack.onFail(WSErrorCode.BROKEN, "net closed");
    }
}
