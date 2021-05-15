package com.ywl5320.wlmedia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by ywl5320 on 2020/3/16
 */
public class WlSeekBar extends View {

    private Paint paint;
    private int width;
    private int height;
    private int color_bg;
    private int color_buffer;
    private int color_progress;
    private int color_thumb_normal;
    private int color_thumb_touch;
    private float progress = 0f;//0 ~ 1f
    private float progress_buffer = 0f;//0 ~ 1f

    private float w_bg = 4;
    private float top;
    private float bottom;
    private float radius = 10;
    private float cy;

    private float moveX;
    private boolean touch = false;
    private RectF rectF;
    private boolean isRound = false;

    private OnWlSeekBarChangeListener onWlSeekBarChangeListener;

    public WlSeekBar(Context context) {
        this(context, null);
    }

    public WlSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        w_bg = dip2px(context, 2);
        radius = dip2px(context, 5);
        color_bg = Color.parseColor("#4AB8B8B8");
        color_buffer = Color.parseColor("#FF999999");
        color_progress = Color.parseColor("#FF0077DD");
        color_thumb_normal = Color.parseColor("#FF0077DD");
        color_thumb_touch = Color.parseColor("#FFFF9800");
    }

    public void setBgHeight(int bgheight)
    {
        if(bgheight <= 0)
        {
            return;
        }
        this.w_bg = dip2px(getContext(), bgheight);
    }

    public void setThumbRadius(int radius)
    {
        if(radius <= 0)
        {
            return;
        }
        this.radius = dip2px(getContext(), radius);
    }


    public void setColorBg(int color)
    {
        color_bg = getResources().getColor(color);
    }

    public void setColorBuffer(int colorBuffer)
    {
        color_buffer = getResources().getColor(colorBuffer);
    }

    public void setColorProgress(int color)
    {
        color_progress = getResources().getColor(color);
    }

    public void setColorThumbNormal(int color)
    {
        color_thumb_normal = getResources().getColor(color);
    }

    public void setColorThumbTouch(int color)
    {
        color_thumb_touch = getResources().getColor(color);
    }

    public void setOnWlSeekBarChangeListener(OnWlSeekBarChangeListener onWlSeekBarChangeListener) {
        this.onWlSeekBarChangeListener = onWlSeekBarChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        top = (height-w_bg) / 2;
        bottom = (height-w_bg) / 2 + w_bg;
        cy = height / 2;
        if(rectF == null)
        {
            rectF = new RectF(0, top, width, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        paint.setColor(color_bg);
        rectF.left = 0;
        rectF.top = top;
        rectF.right = width;
        rectF.bottom = bottom;
        if(!isRound)
        {
            canvas.drawRect(rectF, paint);
        }
        else{
            canvas.drawRoundRect(rectF, w_bg, w_bg, paint);
        }

        //绘制进度
        paint.setColor(color_buffer);
        rectF.left = 0;
        rectF.top = top;
        rectF.right = progress_buffer * width;
        rectF.bottom = bottom;
        if(!isRound)
        {
            canvas.drawRect(rectF, paint);
        }
        else{
            canvas.drawRoundRect(rectF, w_bg, w_bg, paint);
        }

        //绘制进度
        paint.setColor(color_progress);
        rectF.left = 0;
        rectF.top = top;
        rectF.right = progress * width;
        rectF.bottom = bottom;
        if(!isRound)
        {
            canvas.drawRect(rectF, paint);
        }
        else{
            canvas.drawRoundRect(rectF, w_bg, w_bg, paint);
        }

        if(!touch)
        {
            paint.setColor(color_thumb_normal);
        }
        else
        {
            paint.setColor(color_thumb_touch);
        }
        if(progress * width < radius)
        {
            canvas.drawCircle(radius, cy, radius, paint);
        }
        else if(progress * width > (width - radius))
        {
            canvas.drawCircle(width - radius, cy, radius, paint);
        }
        else
        {
            canvas.drawCircle(progress * width, cy, radius, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touch = true;
                moveX = event.getX();
                setProgress(moveX / width);
                if(onWlSeekBarChangeListener != null)
                {
                    onWlSeekBarChangeListener.onStart(progress);
                }

            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                setProgress(moveX / width);
                if(onWlSeekBarChangeListener != null)
                {
                    onWlSeekBarChangeListener.onMove(progress);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                moveX = event.getX();
                setProgress(moveX / width);
                if(onWlSeekBarChangeListener != null)
                {
                    onWlSeekBarChangeListener.onEnd(progress);
                }
                touch = false;
                break;
        }
        return true;
    }

    public void setRound(boolean isRound)
    {
        this.isRound = isRound;
        invalidate();
    }

    public void setProgress(double progress)
    {
        setProgress(progress, progress_buffer);
    }

    public void setProgress(double progress, double progress_buffer)
    {
        if(progress < 0f)
        {
            progress = 0f;
        }
        if(progress > 1f)
        {
            progress = 1f;
        }

        if(progress_buffer < 0f)
        {
            progress_buffer = 0f;
        }
        if(progress_buffer > 1f)
        {
            progress_buffer = 1f;
        }
        this.progress_buffer = (float) progress_buffer;
        this.progress = (float) progress;
        invalidate();
    }

    public interface OnWlSeekBarChangeListener
    {
        void onStart(float value);

        void onMove(float value);

        void onEnd(float value);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
