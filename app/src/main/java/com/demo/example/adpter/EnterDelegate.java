package com.demo.example.adpter;

import android.view.ViewGroup;

import com.demo.example.R;
import com.demo.example.model.EnterData;

/**
 * Created by hzsunyj on 2017/10/17.
 */

public class EnterDelegate extends BaseDelegate<EnterData> {

    public static final int TEXT_HOLDER = 0;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TEXT_HOLDER:
                return new EnterTextHolder(parent, getItemView(parent, viewType));
        }
        return null;
    }

    @Override
    public int getItemViewType(EnterData data) {
        return data.holderType;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TEXT_HOLDER:
                return R.layout.view_holder_enter_text;
        }
        return 0;
    }
}
