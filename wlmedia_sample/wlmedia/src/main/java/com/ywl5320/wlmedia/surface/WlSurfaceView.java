package com.ywl5320.wlmedia.surface;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.util.WlDisplayUtil;

public class WlSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private WlMedia wlMedia;
    private WlOnVideoViewListener onVideoViewListener;
    private boolean isInit = false;

    private float x_down = 0;
    private float y_down = 0;
    private int type = -1;
    private double seek_time = 0;
    private boolean ismove = false;
    private float startMoveOffsetLength = 0;
    private int clickCount = 0;
    private double move_offset_percent = 0;
    private float offset_move_x = 0;
    private float offset_move_y = 0;
    private boolean isXmoving = false;

    private static final int MOVE_X = 1;
    private static final int MOVE_Y = 2;

    public static final int MOVE_START = 3;
    public static final int MOVE_ING = 4;
    public static final int MOVE_STOP = 5;

    public WlSurfaceView(Context context) {
        this(context, null);
    }

    public WlSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isInit = false;
        startMoveOffsetLength = WlDisplayUtil.dip2px(context, 30);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.RGBA_8888);
    }

    public void setWlMedia(WlMedia wlMedia) {
        this.wlMedia = wlMedia;
    }

    public void setOnVideoViewListener(WlOnVideoViewListener onVideoViewListener) {
        this.onVideoViewListener = onVideoViewListener;
    }

    public void enableAlphaVideo(boolean enable)
    {
        setZOrderOnTop(enable);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(!isInit)
        {
            if(wlMedia != null)
            {
                wlMedia.setSurface(holder.getSurface());
            }
            if(onVideoViewListener != null)
            {
                isInit = true;
                onVideoViewListener.initSuccess();
            }
        }
        if(wlMedia != null)
        {
            wlMedia.onSurfaceChange(width, height, holder.getSurface());
            WlLog.d("onSurfaceChange " + width + ", " + height + "," + hashCode());
        }
        if(onVideoViewListener != null)
        {
            onVideoViewListener.onSurfaceChange(width, height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(wlMedia != null)
        {
            wlMedia.onSurfaceDestroy();
        }
    }

    public Surface getSurface()
    {
        if(getHolder() != null)
        {
            return getHolder().getSurface();
        }
        return null;
    }

    public boolean isInit() {
        return isInit;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(wlMedia == null)
        {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                isXmoving = false;
                ismove = false;
                type = -1;
                x_down = event.getX();
                y_down = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offset_move_x = event.getX() - x_down;
                offset_move_y = event.getY() - y_down;
                if(type == -1)
                {
                    if(Math.abs(offset_move_x) > Math.abs(startMoveOffsetLength))
                    {
                        type = MOVE_X;
                    }
                    else if(Math.abs(offset_move_y) > Math.abs(startMoveOffsetLength))
                    {
                        type = MOVE_Y;
                    }
                }
                if(type == MOVE_Y)
                {
                    if(x_down < getWidth() / 2)
                    {
                        if(onVideoViewListener != null)
                        {
                            if(!ismove)
                            {
                                if(offset_move_y > 0)
                                {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength);
                                }
                                else
                                {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength) * -1;
                                }
                                onVideoViewListener.moveLeft(-move_offset_percent, MOVE_START);
                            }
                            else
                            {
                                move_offset_percent = (offset_move_y  - startMoveOffsetLength) / (getHeight() / 2);
                                onVideoViewListener.moveLeft(-move_offset_percent, MOVE_ING);
                            }
                        }
                    }
                    else
                    {
                        if(onVideoViewListener != null)
                        {
                            if(!ismove)
                            {
                                if(offset_move_y > 0)
                                {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength);
                                }
                                else
                                {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength) * -1;
                                }
                                onVideoViewListener.moveRight(-move_offset_percent, MOVE_START);
                            }
                            else
                            {
                                move_offset_percent = (offset_move_y  - startMoveOffsetLength) / (getHeight() / 2);
                                onVideoViewListener.moveRight(-move_offset_percent, MOVE_ING);
                            }
                        }
                    }
                    ismove = true;
                }
                else if(type == MOVE_X)
                {
                    if(onVideoViewListener != null)
                    {
                        if(wlMedia != null && wlMedia.getDuration() > 0)
                        {
                            if(!ismove)
                            {
                                if(offset_move_x > 0)
                                {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength) * -1;
                                }
                                else
                                {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength);
                                }
                                onVideoViewListener.moveX(seek_time, MOVE_START);
                            }
                            else
                            {
                                move_offset_percent = (offset_move_x + startMoveOffsetLength) / (getWidth() * 3);
                                seek_time = wlMedia.getNowTime() + move_offset_percent * wlMedia.getDuration();
                                if(seek_time < 0)
                                {
                                    seek_time = 0;
                                }
                                if(seek_time > wlMedia.getDuration())
                                {
                                    seek_time = wlMedia.getDuration();
                                }
                                isXmoving = true;
                                onVideoViewListener.moveX(seek_time, MOVE_ING);
                            }
                        }
                    }
                    ismove = true;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(ismove)
                {
                    if(type == MOVE_X)
                    {
                        if(onVideoViewListener != null)
                        {
                            if(seek_time < 0)
                            {
                                seek_time = 0;
                            }
                            if(seek_time > wlMedia.getDuration())
                            {
                                seek_time = wlMedia.getDuration();
                            }
                            if(isXmoving)
                            {
                                onVideoViewListener.moveX(seek_time, MOVE_STOP);
                            }
                            else
                            {
                                onVideoViewListener.moveX(-1, MOVE_STOP);
                            }
                            seek_time = 0;
                        }
                    }
                    else if(type == MOVE_Y)
                    {
                        if(x_down < getWidth() / 2)
                        {
                            if(onVideoViewListener != null)
                            {
                                onVideoViewListener.moveLeft(-move_offset_percent, MOVE_STOP);
                            }
                        }
                        else
                        {
                            if(onVideoViewListener != null)
                            {
                                onVideoViewListener.moveRight(-move_offset_percent, MOVE_STOP);
                            }
                        }
                    }
                }
                else
                {
                    clickCount++;
                    handler.postDelayed(runnable, 200);
                }
                break;
        }
        return true;
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(clickCount == 1)
            {
                if(onVideoViewListener != null)
                {
                    onVideoViewListener.onSingleClick();
                }
            }
            else if(clickCount == 2)
            {
                if(onVideoViewListener != null)
                {
                    onVideoViewListener.onDoubleClick();
                }
            }
            clickCount = 0;
            handler.removeCallbacksAndMessages(null);
        }
    };

    public void updateWlMedia(WlMedia wlMedia)
    {
        this.wlMedia = wlMedia;
        if(wlMedia != null)
        {
            wlMedia.onSurfaceChange(getWidth(), getHeight(), getHolder().getSurface());
        }
    }
}
