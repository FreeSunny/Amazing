package com.demo.example.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

/**
 * Created by hzsunyj on 2017/12/18.
 */

public class FixedNestedScrollView extends NestedScrollView {

    public FixedNestedScrollView(Context context) {
        super(context);
        init();
    }

    public FixedNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixedNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
//                ViewParent parent = getParent();
                //                while (!(parent instanceof SwipeRefreshLayout)) {
                //                    parent = getParent();
                //                }
                //                SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) parent;
                //                refreshLayout.setEnabled(getScrollY() == 0);
            }
        });
    }

}
