package com.ywl5320.wlmedia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


/**
 * @author ywl5320
 * @date 2020/6/30
 */
public class WlCircleLoadView extends View {

    private Paint paint;

    private float centerX;
    private float centerY;
    private float minLen;
    private int count = 0;
    private boolean exit = false;


    public WlCircleLoadView(Context context) {
        this(context, null);
    }

    public WlCircleLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlCircleLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        minLen = centerX < centerY ? centerX : centerY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(count, centerX, centerY);
        for(int i = 0; i < 9; i++)
        {
            canvas.drawCircle(centerX + minLen - dip2px(getContext(),9 + 1), centerY, dip2px(getContext(), 1.0f + 0.3f * i), paint);
            canvas.rotate(40, centerX, centerY);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        exit = false;
        startAnimal();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        exit = true;
        handler.removeCallbacksAndMessages(null);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(exit)
            {
                return;
            }
            count+=5;
            if(count >= 360)
            {
                count = 0;
            }
            invalidate();
            handler.postDelayed(runnable, 10);
        }
    };

    public void startAnimal()
    {
        handler.postDelayed(runnable, 0);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setColor(int color)
    {
        if(paint != null)
        {
            paint.setColor(getResources().getColor(color));
        }
    }
}
