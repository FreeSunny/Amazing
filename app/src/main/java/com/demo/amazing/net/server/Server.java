package com.demo.amazing.net.server;

import android.support.annotation.NonNull;

import com.demo.amazing.net.action.Action;
import com.demo.amazing.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.ByteString;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class Server {

    MockWebServer mockWebServer;

    private String wsUrl;

    public Server() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();
    }

    public void getServerUrl() {
        String hostName = mockWebServer.getHostName();
        int port = mockWebServer.getPort();
        StringBuilder sb = new StringBuilder();
        sb.append("ws://").append(hostName).append(":").append(port);
        wsUrl = sb.toString();
    }

    private void start() {
        mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                LogUtil.e("server onOpen");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                LogUtil.e("server onMessage=" + text);
                webSocket.send(getText(text));
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                LogUtil.e("server onClosed: code=" + code + " reason=" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                LogUtil.e("server onFailure: t=" + t.getMessage());
            }
        }));
        getServerUrl();
    }

    AtomicInteger increment = new AtomicInteger(0);

    @NonNull
    private String getText(String text) {
        int action = Action.UNKNOWN_ACTION;
        String desc = "default";
        try {
            JSONObject object = new JSONObject(text);
            action = object.optInt("action");
            desc = object.optString("desc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject();
        try {
            if (action == Action.LINK_ACTION) {
                object.putOpt("action", Action.LINK_ACTION);
                object.putOpt("desc", "link: response" + increment.getAndIncrement());
            } else {
                object.putOpt("action", action);
                object.putOpt("desc", "response: hi" + desc + increment.getAndIncrement());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public String getWsUrl() {
        return wsUrl;
    }
}
