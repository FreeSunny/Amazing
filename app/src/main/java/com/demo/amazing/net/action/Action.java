package com.demo.amazing.net.action;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class Action {

    public static final int UNKNOWN_ACTION = -1;

    public static final int LINK_ACTION = 1;

    public static final int FAIL_ACTION = 2;

    public static final int VOTE_ACTION = 3;

    public static final int DESC_ACTION = 4;

    public int action;// 动作类型

    public String desc;

    public Action(int action) {
        this.action = action;
    }

    public Action(int action, String desc) {
        this.action = action;
        this.desc = desc;
    }

    public static Action builder() {
        return new Action(UNKNOWN_ACTION, null);
    }
}
