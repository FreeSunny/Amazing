package com.demo.example.net.action;

import org.json.JSONObject;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class DescAction extends Action {

    public DescAction() {
        super(Action.DESC_ACTION);
    }

    public DescAction(String desc) {
        super(Action.DESC_ACTION, desc);
    }

    public static DescAction builder(JSONObject object) {
        DescAction descAction = new DescAction();
        descAction.desc = object.optString("desc");
        return descAction;
    }
}
