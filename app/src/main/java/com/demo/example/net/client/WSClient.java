package com.demo.example.net.client;

import com.demo.example.net.WSCallBack;
import com.demo.example.net.WSListener;
import com.demo.example.net.config.WSErrorCode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * web socket client
 * Created by hzsunyj on 2017/8/28.
 */

public final class WSClient {

    /**
     * ok http client
     */
    private OkHttpClient httpClient;

    /**
     * web socket
     */
    private WebSocket webSocket;

    /**
     *
     */
    private WSClient() {
        httpClient = OkHttp.getInstance().getHttpClient();

    }

    /**
     * @return
     */
    public static WSClient getInstance() {
        return new WSClient();
    }

    /**
     * connect
     *
     * @param wsUrl
     * @param callBack
     */
    public void connect(String wsUrl, final WSCallBack callBack) {
        Request request = new Request.Builder().url(wsUrl).build();
        webSocket = httpClient.newWebSocket(request, new WSListener(callBack));
    }


    /**
     * dis connect
     */
    public void disConnect() {
        if (webSocket != null) {
            webSocket.close(WSErrorCode.BROKEN, "broken");
        }
    }

    /**
     * enqueue
     *
     * @param text
     * @return
     */
    public boolean send(String text) {
        boolean accept = false;
        if (webSocket != null) {
            accept = webSocket.send(text);
        }
        return accept;
    }

    /**
     * destroy
     */
    public void onDestroy() {
        disConnect();
        webSocket = null;
    }
}
