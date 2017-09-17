package com.demo.example.net.action;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * sample
 * <p>
 * Created by hzsunyj on 2017/8/30.
 */

public class VoteAction extends Action {

    public VoteAction() {
        super(VOTE_ACTION);
    }

    public static VoteAction builder(String msg) {
        try {
            return builder(new JSONObject(msg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VoteAction builder(JSONObject object) {
        VoteAction voteAction = new VoteAction();
        voteAction.desc = object.optString("desc");
        return voteAction;
    }
}
