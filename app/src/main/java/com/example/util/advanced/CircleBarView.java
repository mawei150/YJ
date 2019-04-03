package com.example.util.advanced;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author MW
 * @date 2019/4/2
 * <p>
 * 描述：圆弧进度条
 */

public class CircleBarView  extends View {

    private Paint mPaint;//画笔
    private Paint mProgressPaint;//画圆弧的画笔

    public CircleBarView(Context context) {
        super(context);
    }

    public CircleBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




}
