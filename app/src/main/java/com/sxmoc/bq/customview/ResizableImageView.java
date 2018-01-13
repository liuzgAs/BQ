package com.sxmoc.bq.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ResizableImageView extends AppCompatImageView {
    public ResizableImageView(Context context) {
        super(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = this.getDrawable();
        if(d != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int)Math.ceil((double)((float)width * (float)d.getIntrinsicHeight() / (float)d.getIntrinsicWidth()));
            this.setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}