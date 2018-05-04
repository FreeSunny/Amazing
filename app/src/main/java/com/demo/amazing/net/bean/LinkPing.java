package com.demo.amazing.net.bean;

import android.text.TextUtils;

import com.demo.amazing.net.action.Action;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class LinkPing {

    public String linkData;

    public String linkPing() {
        if (!TextUtils.isEmpty(linkData)) {
            return linkData;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("action", Action.LINK_ACTION);
            jsonObject.putOpt("desc", "heart beat");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return linkData = jsonObject.toString();
    }
}
