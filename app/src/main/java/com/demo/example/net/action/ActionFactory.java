package com.demo.example.net.action;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class ActionFactory {

    /**
     * @param msg
     * @return
     * @throws JSONException
     */
    public static Action getAction(String msg) {
        JSONObject object;
        try {
            object = new JSONObject(msg);
        } catch (JSONException e) {
            return null;
        }
        int action = object.optInt("action");
        return getAction(action, object);
    }

    /**
     * dispatch ui action
     *
     * @param action
     * @param object
     * @return
     */
    public static Action getAction(int action, JSONObject object) {
        switch (action) {
            case Action.VOTE_ACTION:
                return VoteAction.builder(object);
            case Action.DESC_ACTION:
                return DescAction.builder(object);
        }
        return Action.builder();
    }

    public static Action getLinkAction(int linkStatus, String desc) {
        return new LinkAction(linkStatus, desc);
    }

    public static Action getLinkAction(int linkStatus, int code, String desc) {
        return new LinkAction(linkStatus, code, desc);
    }
}
