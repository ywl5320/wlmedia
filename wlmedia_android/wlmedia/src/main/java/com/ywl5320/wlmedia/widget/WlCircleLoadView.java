package com.ywl5320.wlmedia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/17
 */
public class WlCircleLoadView extends View {

    private static final int FRAME_DELAY = 16; // 约60 FPS
    private static final float BASE_RADIUS_DP = 1.0f;
    private static final float RADIUS_STEP_DP = 0.3f;
    private static final int CIRCLE_COUNT = 9;
    private static final float OFFSET_DP = 10f;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Handler handler = new Handler();
    private final float[] radiusArray = new float[CIRCLE_COUNT];

    private float centerX;
    private float centerY;
    private float offsetX;
    private int count;
    private boolean exit;

    public WlCircleLoadView(Context context) {
        this(context, null);
    }

    public WlCircleLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlCircleLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2f;
        centerY = h / 2f;
        float minLen = Math.min(centerX, centerY);

        // 预计算所有尺寸
        offsetX = minLen - dip2px(OFFSET_DP);
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            radiusArray[i] = dip2px(BASE_RADIUS_DP + RADIUS_STEP_DP * i);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(count, centerX, centerY);

        for (int i = 0; i < CIRCLE_COUNT; i++) {
            canvas.drawCircle(centerX + offsetX, centerY, radiusArray[i], paint);
            canvas.rotate(40, centerX, centerY);
        }

        canvas.restore();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        exit = false;
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        exit = true;
        handler.removeCallbacksAndMessages(null);
    }

    private void startAnimation() {
        handler.post(new AnimationRunnable(this));
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    private int dip2px(float dpValue) {
        return (int) (dpValue * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static class AnimationRunnable implements Runnable {
        private final WeakReference<WlCircleLoadView> viewRef;

        AnimationRunnable(WlCircleLoadView view) {
            this.viewRef = new WeakReference<>(view);
        }

        @Override
        public void run() {
            WlCircleLoadView view = viewRef.get();
            if (view == null || view.exit) return;

            view.count = (view.count + 5) % 360;
            view.invalidate();
            view.handler.postDelayed(this, FRAME_DELAY);
        }
    }
}
