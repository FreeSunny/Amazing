package com.demo.amazing.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.amazing.R;
import com.demo.amazing.widget.FadingTextView;

import java.util.ArrayList;
import java.util.List;

import static com.demo.amazing.R.id.recycler_view;

public class FadingActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, FadingActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView recyclerView;

    private List<String> datas;

    private Paint paint;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fading);
        findViews();
        init();
        setViewsListener();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(recycler_view);
    }

    private void setViewsListener() {
        getDatas();
        GridLayoutManager layout = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new DataAdapter());
    }

    public List<String> getDatas() {
        datas = new ArrayList<>(50);
        for (int i = 0; i < 50; ++i) {
            datas.add(new String("我爱北京天安门" + i));
        }
        return datas;
    }

    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {


        @Override
        public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_view,
                    parent, false));
        }

        @Override
        public void onBindViewHolder(DataViewHolder holder, int position) {
            holder.textView.setText(datas.get(position));
            holder.itemView.setAlpha(1.0f);
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }

        public class DataViewHolder extends RecyclerView.ViewHolder {

            FadingTextView textView;

            public DataViewHolder(View itemView) {
                super(itemView);
                textView = (FadingTextView) itemView.findViewById(R.id.text_view);
            }
        }
    }
}
