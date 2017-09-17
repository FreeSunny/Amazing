package com.demo.example.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.demo.example.R;
import com.demo.example.widget.FadingTextView;

import java.util.ArrayList;
import java.util.List;

import static com.demo.example.R.id.recycler_view;

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
        //paint.setShader(new LinearGradient())
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(recycler_view);
    }

    private void setViewsListener() {
        getDatas();
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new DataAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //onScroll(recyclerView, dx, dy);
            }
        });
    }

    private void alphaTween() {
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(300);
        imageView.startAnimation(alpha);
    }

    private void alphaOB() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.0f).setDuration(300);
        animator.start();
    }

    int lastPos = 0;

    int offsetY = 0;

    private void onScroll(RecyclerView recyclerView, int dx, int dy) {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstPos = manager.findFirstVisibleItemPosition();
        if (firstPos != lastPos) {// reset
            setItemAlpha(recyclerView, 0, lastPos);
            lastPos = firstPos;
            offsetY = 0;
        }
        offsetY += dy;
        setItemAlpha(recyclerView, offsetY, firstPos);
    }

    private void setItemAlpha(RecyclerView recyclerView, int allDy, int pos) {
        DataAdapter.DataViewHolder holder = (DataAdapter.DataViewHolder) recyclerView
                .findViewHolderForAdapterPosition(pos);
        if (holder == null) {
            return;
        }
        View itemView = holder.itemView;
        float alpha = 1.0f;
        if (allDy != 0) {
            int height = itemView.getHeight();
            if (allDy > 0) {
                alpha = (float) ((height - allDy) * 1.0 / height);
            } else {
                alpha = (float) (Math.abs(allDy) * 1.0 / height);
            }
        }
        holder.textView.setFading(alpha);
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
