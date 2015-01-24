package com.arthurmb.recyclerviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;



/** An image view which always remains square with respect to its width. */
final class SquaredImage2 extends ImageView {
    public SquaredImage2(Context context) {
        super(context);
    }

    public SquaredImage2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(5000,5000);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}