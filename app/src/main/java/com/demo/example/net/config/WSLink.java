package com.demo.example.net.config;

/**
 * web socket link info
 * <p>
 * sample: ws://host:port
 * <p>
 * Created by hzsunyj on 2017/8/28.
 */

public final class WSLink {

    /**
     * secure web socket
     */
    public static final String WSS = "wss://";

    /**
     * normal web socket
     */
    public static final String WS = "ws://";

    /**
     * link host
     */
    private String host;

    /**
     * link port
     */
    private String port;

    /**
     * weather secure mode
     */
    private boolean secure;

    /**
     * set wsUrl
     */
    private String wsUrl;

    public WSLink(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    /**
     * ws
     *
     * @param host
     * @param port
     */
    public WSLink(String host, String port) {
        this(host, port, false);
    }

    /**
     * @param host
     * @param port
     * @param secure
     */
    public WSLink(String host, String port, boolean secure) {
        this.host = host;
        this.port = port;
        this.secure = secure;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * use host and port concat url
     *
     * @return
     */
    public String concatWsUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(isSecure() ? WSS : WS);
        sb.append(host).append(":").append(port);
        return sb.toString();
    }

    /**
     * get ws Url
     *
     * @return
     */
    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }
}
