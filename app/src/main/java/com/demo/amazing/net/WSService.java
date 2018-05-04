package com.demo.amazing.net;

import android.os.Handler;
import android.os.Looper;

import com.demo.amazing.net.action.Action;
import com.demo.amazing.net.action.ActionFactory;
import com.demo.amazing.net.bean.LinkPing;
import com.demo.amazing.net.client.WSClient;
import com.demo.amazing.net.config.LinkStatus;
import com.demo.amazing.net.config.WSLink;
import com.demo.amazing.net.observer.Observable;
import com.demo.amazing.net.observer.Observer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * web socket service
 * <p>
 * Created by hzsunyj on 2017/8/28.
 */

public class WSService {

    private static WSClient client;

    private static WSService service;

    private WSLink wsLink;

    /**
     * dispatch ui thread
     */
    private Handler handler;

    private Observable observable;

    private ScheduledFuture<?> heartbeat;

    private ScheduledExecutorService executor;

    private LinkStatus status;

    private LinkPing linkPing;

    private WSService() {
        client = WSClient.getInstance();
        handler = new Handler(Looper.getMainLooper());
        observable = new Observable();
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public static WSService getService() {
        if (service == null) {
            service = new WSService();
        }
        return service;
    }

    /**
     * link remote service
     */
    public void link(String url) {
        status = new LinkStatus();
        wsLink = new WSLink(url);
        linkPing = new LinkPing();
        if (isConnected()) {
            disConnect();
        }
        connect(url);
    }

    /**
     * register observer
     *
     * @param observer
     */
    public void register(Observer observer) {
        if (observable != null) {
            observable.registerObserver(observer);
        }
    }

    /**
     * unregister observer
     *
     * @param observer
     */
    public void unregister(Observer observer) {
        if (observable != null) {
            observable.unrigisterObserver(observer);
        }
    }

    /**
     * may be relink
     */
    public void reLink() {
        if (wsLink == null) {
            notifyAction("服务器链接错误");
            return;
        }
        link(wsLink.getWsUrl());
    }

    /**
     * is link already connected
     *
     * @return
     */
    private boolean isConnected() {
        return status.isConnected();
    }

    /**
     * dis connect cur link
     */
    private void disConnect() {
        status.setStatus(LinkStatus.BROKEN);
        client.disConnect();
        stopHeartbeat();
    }

    /**
     * link
     *
     * @param url
     */
    private void connect(String url) {
        status.setStatus(LinkStatus.CONNECTTING);
        client.connect(url, new WSCallBack() {
            @Override
            public void isConnected() {// may be need notify ui
                status.setStatus(LinkStatus.CONNECTED);
                startHeartbeat(5);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyAction(ActionFactory.getLinkAction(LinkStatus.CONNECTED, "success"));
                    }
                });
            }

            @Override
            public void close() {
                disConnect();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyAction(ActionFactory.getLinkAction(LinkStatus.BROKEN, "close"));
                    }
                });
            }

            @Override
            public void onMessage(final String text) { // dispatch ui thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyAction(ActionFactory.getAction(text));
                    }
                });
            }

            @Override
            public void onFail(final int code, final String err) {
                disConnect();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyAction(ActionFactory.getLinkAction(LinkStatus.BROKEN, code, err));
                    }
                });
            }
        });
    }


    /**
     * send action
     */
    private void notifyAction(String err) {
        notifyAction(ActionFactory.getLinkAction(LinkStatus.BROKEN, err));
    }

    private void notifyAction(Action action) {
        if (observable != null) {
            observable.onAction(action);
        }
    }

    /**
     * send heart beat
     *
     * @param interval
     */
    private void startHeartbeat(int interval) {
        heartbeat = executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                sendHeart();
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    private void sendHeart() {
        send(linkPing.linkPing());
    }

    /**
     * stop heart beat
     */
    private void stopHeartbeat() {
        if (heartbeat != null) {
            heartbeat.cancel(true);
            heartbeat = null;
        }
    }

    /**
     * client send text
     */
    public void send(String text) {
        if (status.isBroken()) {
            reLink();
        }
        boolean accept = client.send(text);
        if (!accept) {
            notifyAction("网络已断开,请稍后再试");
        }
    }

    public void onDestroy() {
        if (client != null) {
            client.onDestroy();
        }
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
        disConnect();
        handler = null;
        service = null;
    }
}
