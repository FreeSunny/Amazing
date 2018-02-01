package com.demo.example.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.fragment.TabFragment;
import com.demo.example.util.LinkMovement;
import com.demo.example.util.LogUtil;


public class NestScrollingActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private NestedScrollView scrollView;

    private SwipeRefreshLayout refreshLayout;

    private AppBarLayout appBarLayout;

    private Handler handler;

    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        init();
    }

    private void findViews() {
        viewPager = findViewById(R.id.data_pager);
        tabLayout = findViewById(R.id.tab_layout);
        scrollView = findViewById(R.id.nested_scroll_view);
        refreshLayout = findViewById(R.id.refresh_layout);
        appBarLayout = findViewById(R.id.app_bar);
        content = findViewById(R.id.textView_content);
    }

    /**
     *
     */
    private void init() {
        handler = new Handler(getMainLooper());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (!refreshLayout.isRefreshing()) {
                    refreshLayout.setEnabled(verticalOffset == 0);
                }
                LogUtil.e("verticalOffset=" + verticalOffset);
            }
        });
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

        // fix 文本有点击事件会拦截底部事件
        content.setOnTouchListener(LinkMovement.getInstance());
    }

    private class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new TabFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB" + position;
        }
    }

}
