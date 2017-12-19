package com.demo.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.adpter.BaseAdapter;
import com.demo.example.adpter.BaseDelegate;
import com.demo.example.adpter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzsunyj on 2017/12/18.
 */

public class TabFragment extends Fragment {

    RecyclerView recyclerView;

    private List<String> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        init();
    }

    private void findViews() {
        recyclerView = getView().findViewById(R.id.list);
    }

    private void init() {
        getDatas();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BaseAdapter<String>(datas, new BaseDelegate() {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new StringViewHolder(parent, getItemView(parent, viewType));
            }

            @Override
            public int getItemViewType(Object data) {
                return 0;
            }

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.tab_item_view_holder;
            }
        }));
    }

    private class StringViewHolder extends BaseViewHolder<String> {


        private TextView textView;

        /**
         * @param parent current no use, may be future use
         * @param view
         */
        public StringViewHolder(ViewGroup parent, View view) {
            super(parent, view);
        }

        @Override
        public void findViews() {
            textView = itemView.findViewById(R.id.content);
        }

        @Override
        public void onBindViewHolder(String data) {
            textView.setText(data);
        }
    }

    private void getDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            datas.add("content" + i);
        }
    }
}
