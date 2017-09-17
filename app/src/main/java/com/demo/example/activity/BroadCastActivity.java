package com.demo.example.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.example.R;

import java.io.Serializable;

public class BroadCastActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, BroadCastActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borad_cast);
        findViews();
    }

    static class SerializableObject implements Serializable {

        static final long serialVersionUID = 42L;

        SerializableObject() {
            super();
        }

    }

    private void findViews() {
        findViewById(R.id.send_bc).setOnClickListener(this);
        findViewById(R.id.send_share).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_bc: {
                Intent intent = new Intent();
                intent.setAction("im.xxx.sdk.Intent.ACTION_HANDLE_APP_UNREGISTER");
                intent.putExtra("this_is_a_random_serializable_extra_for_test_general_reject_server", new
                        SerializableObject());
                sendBroadcast(intent);
                break;
            }
            case R.id.send_share: {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.setComponent(new ComponentName("im.xxx", "im.xxx.activity.share.ShareToSessionActivity"));
                intent.putExtra("android.intent.extra.TEXT", "*+./:-%&\"");
                intent.putExtra("this_is_a_random_serializable_extra_for_test_general_reject_server", new
                        SerializableObject());
                startActivity(intent);
                break;
            }
        }
    }
}
