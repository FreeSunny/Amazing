package com.demo.example.net.action;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class LinkAction extends Action {

    public int status;

    public int code;

    public LinkAction(int status, String desc) {
        this(status, 0, desc);
    }

    public LinkAction(int status, int code, String desc) {
        super(Action.LINK_ACTION);
        this.status = status;
        this.code = code;
        this.desc = desc;
    }
}
