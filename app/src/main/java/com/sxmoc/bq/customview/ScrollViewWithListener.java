package com.sxmoc.bq.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollViewWithListener extends ScrollView {
    private ScrollViewWithListener.OnScrollChangedListener listener;

    public void setOnScrollChangedListener(ScrollViewWithListener.OnScrollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.listener.onScrollChanged(t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public ScrollViewWithListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int var1);
    }
}