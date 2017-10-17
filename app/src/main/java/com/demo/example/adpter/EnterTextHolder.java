package com.demo.example.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.model.EnterData;

/**
 * Created by hzsunyj on 2017/10/17.
 */

public class EnterTextHolder extends BaseViewHolder<EnterData> {

    private TextView desc;

    /**
     */
    public EnterTextHolder(ViewGroup parent, View view) {
        super(parent, view);
    }

    @Override
    public void findViews() {
        desc = itemView.findViewById(R.id.desc_view);
    }

    @Override
    public void onBindViewHolder(EnterData data) {
        desc.setText(data.itemDesc);
    }
}
