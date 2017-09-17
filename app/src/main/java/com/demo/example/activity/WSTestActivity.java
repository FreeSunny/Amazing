package com.demo.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.example.R;
import com.demo.example.net.WSService;
import com.demo.example.net.action.Action;
import com.demo.example.net.observer.Observer;
import com.demo.example.net.server.Server;
import com.demo.example.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WSTestActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    public static void start(Context context) {
        Intent intent = new Intent(context, WSTestActivity.class);
        context.startActivity(intent);
    }

    private Button init;

    private Button connect;

    private Button send;

    private EditText input;

    private RecyclerView recyclerView;

    private WSService service;

    private Server server;

    RecyclerView.Adapter adapter;

    private ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wstest);
        findViews();
        setViewsListener();
        initRecyclerView();
    }

    /**
     * find views
     */
    private void findViews() {
        init = (Button) findViewById(R.id.init);
        connect = (Button) findViewById(R.id.connect);
        send = (Button) findViewById(R.id.send);
        input = (EditText) findViewById(R.id.input_box);
        recyclerView = (RecyclerView) findViewById(R.id.data_view);
    }

    /**
     *
     */
    private void setViewsListener() {
        init.setOnClickListener(this);
        connect.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    /**
     *
     */
    private void initRecyclerView() {
        dataList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerView.Adapter() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(new TextView(parent.getContext())) {};
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(dataList.get(position));
                textView.setTextColor(getResources().getColor(position % 2 == 0 ? R.color.black : R.color.colorAccent));
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.init:
                onInit();
                break;
            case R.id.connect:
                onConnect();
                break;
            case R.id.send:
                onSend();
                break;
        }
    }

    private void onInit() {
        server = new Server();
//        try {
        //            JSONObject jsonObject = new JSONObject();
        //            jsonObject.putOpt("key1", "value1");
        //            JSONObject subj = new JSONObject();
        //            subj.putOpt("key2", "value2");
        //            subj.putOpt("key3", "value3");
        //            jsonObject.put("sub", subj);
        //            jsonObject.putOpt("key4", "value4");
        //
        //            LogUtil.e(jsonObject.toString());
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
    }

    private void onConnect() {
        service = WSService.getService();
        service.register(this);
        String wsUrl = server.getWsUrl();
        if (TextUtils.isEmpty(wsUrl)) {
            showToast("link url is empty");
        } else {
            LogUtil.e("link=" + wsUrl);
        }
        service.link(wsUrl);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void onSend() {
        String message = input.getText().toString();
        if (TextUtils.isEmpty(message)) {
            showToast("input empty");
            return;
        }
        service.send(getWrapper(message));
        dataList.add(message);
        adapter.notifyDataSetChanged();
    }

    private String getWrapper(String message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("action", Action.DESC_ACTION);
            jsonObject.putOpt("desc", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public void onAction(Action action) {
        switch (action.action) {
            case Action.DESC_ACTION:
                dataList.add(action.desc);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (service != null) {
            service.unregister(this);
        }
    }
}
