package com.demo.amazing.widget.sudoku;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by hzsunyj on 2017/12/22.
 */

public class Sudoku extends GridLayout {

    public int margin = 4;

    public int paddingTop = 10;

    public Sudoku(Context context) {
        super(context);
        init(context, null);
    }

    public Sudoku(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Sudoku(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }

    public List<String> urls;

    public void setUrls(List<String> urls) {
        this.urls = urls;
        updateView();
    }

    public void reset() {
        if (urls != null) {
            urls.clear();
        }
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setLayoutParams(lp);
        removeAllViews();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == INVISIBLE) {
            setPadding(getPaddingLeft(), 0, getPaddingRight(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), getPaddingBottom());
        }
    }

    private void updateView() {
        removeAllViews();// remove all views
        int size = urls != null ? urls.size() : 0;
        int columnCount = getCol(size);
        for (int i = 0; i < size; ++i) {
            GridLayout.Spec row = GridLayout.spec(i / columnCount);
            GridLayout.Spec col = GridLayout.spec(i % columnCount, 1.0f);
            ImageView imageView = getImageView(i);
            //由于宽（即列）已经定义权重比例 宽设置为0 保证均分
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.rowSpec = row;
            layoutParams.columnSpec = col;

            layoutParams.setMargins(margin, margin, margin, margin);
            addView(imageView, layoutParams);
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams lp = getLayoutParams();
                if (getHeight() > 0 && lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    lp.height = getHeight();
                    setLayoutParams(lp);
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @NonNull
    private ImageView getImageView(final int i) {
        ImageView imageView = new SquareImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //GLImageLoader.displayImage(imageView, urls.get(i));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_LONG).show();
            }
        });
        return imageView;
    }

    private int getCol(int size) {
        if (size == 4) {
            return 2;
        } else if (size <= 3) {
            return size;
        } else if (size >= 5) {
            return 3;
        }
        return 0;
    }

}
