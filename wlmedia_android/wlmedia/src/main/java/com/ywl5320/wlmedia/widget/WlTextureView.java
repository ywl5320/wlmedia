package com.ywl5320.wlmedia.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlAlphaVideoType;
import com.ywl5320.wlmedia.enums.WlMirrorType;
import com.ywl5320.wlmedia.enums.WlRotateType;
import com.ywl5320.wlmedia.enums.WlScaleType;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.util.WlColorUtil;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/19
 */
public class WlTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private WlPlayer wlPlayer;
    private String rgba = "#000000FF";
    private Surface surface;
    private SurfaceTexture surfaceTextur;
    private WlOnVideoViewListener wlOnVideoViewListener;
    private boolean isInit = false;

    private float x_down = 0;
    private float y_down = 0;
    private int type = -1;
    private double seek_time = 0;
    private boolean isMove = false;
    private float startMoveOffsetLength = 0;
    private int clickCount = 0;
    private double move_offset_percent = 0;
    private float offset_move_x = 0;
    private float offset_move_y = 0;
    private boolean isXMoving = false;

    private static final int MOVE_X = 1;
    private static final int MOVE_Y = 2;

    public static final int MOVE_START = 3;
    public static final int MOVE_ING = 4;
    public static final int MOVE_STOP = 5;

    public WlTextureView(Context context) {
        this(context, null);
    }

    public WlTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startMoveOffsetLength = dip2px(context, 30);
        setSurfaceTextureListener(this);
    }

    public void setWlPlayer(WlPlayer wlPlayer) {
        this.wlPlayer = wlPlayer;
    }

    public void setWlPlayer(WlPlayer wlPlayer, String rgba) {
        this.wlPlayer = wlPlayer;
        if (WlColorUtil.isRGBAColor(rgba)) {
            this.rgba = rgba;
        } else {
            this.rgba = "#000000FF";
        }
    }

    public void setAlphaVideoType(WlAlphaVideoType alphaVideoType) {
        if (wlPlayer == null) {
            return;
        }
        if (alphaVideoType == WlAlphaVideoType.WL_ALPHA_VIDEO_DEFAULT) {
            setOpaque(true);
        } else {
            setOpaque(false);
        }
        this.wlPlayer.setAlphaVideoType(alphaVideoType);
    }

    public void setVideoScale(WlScaleType scaleType) {
        if (wlPlayer != null) {
            wlPlayer.setVideoScale(getUniqueNum(), scaleType);
        }
    }

    public void setVideoScale(int scaleWidth, int scaleHeight) {
        if (wlPlayer != null) {
            wlPlayer.setVideoScale(getUniqueNum(), scaleWidth, scaleHeight);
        }
    }

    public void setVideoRotate(WlRotateType rotateType) {
        if (wlPlayer != null) {
            wlPlayer.setVideoRotate(getUniqueNum(), rotateType);
        }
    }

    public void setVideoMirror(WlMirrorType mirrorType) {
        if (wlPlayer != null) {
            wlPlayer.setVideoMirror(getUniqueNum(), mirrorType);
        }
    }

    public long getUniqueNum() {
        return hashCode();
    }

    public void setClearLastVideoFrame(boolean clear) {
        if (wlPlayer != null) {
            wlPlayer.setClearLastVideoFrame(getUniqueNum(), clear);
        }
    }

    public void setOnVideoViewListener(WlOnVideoViewListener onVideoViewListener) {
        this.wlOnVideoViewListener = onVideoViewListener;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Matrix matrix = new Matrix();
        matrix.setScale(1.001f, 1.001f);
        setTransform(matrix);
        if (this.surfaceTextur == null) {
            this.surfaceTextur = surfaceTexture;
        } else {
            setSurfaceTexture(this.surfaceTextur);
        }
        if (!isInit) {
            if (surface == null) {
                surface = new Surface(surfaceTexture);
            }
            if (wlPlayer != null) {
                wlPlayer.setSurface(surface, rgba, getUniqueNum());
            }
            if (wlOnVideoViewListener != null) {
                isInit = true;
                wlOnVideoViewListener.initSuccess();
            }
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        if (wlPlayer != null) {
            wlPlayer.setSurface(surface, rgba, getUniqueNum());
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (wlPlayer != null) {
            wlPlayer.setSurface(null, rgba, getUniqueNum());
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (wlPlayer == null) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isXMoving = false;
                isMove = false;
                type = -1;
                x_down = event.getX();
                y_down = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offset_move_x = event.getX() - x_down;
                offset_move_y = event.getY() - y_down;
                if (type == -1) {
                    if (Math.abs(offset_move_x) > Math.abs(startMoveOffsetLength)) {
                        type = MOVE_X;
                    } else if (Math.abs(offset_move_y) > Math.abs(startMoveOffsetLength)) {
                        type = MOVE_Y;
                    }
                }
                if (type == MOVE_Y) {
                    if (x_down < getWidth() / 2.0) {
                        if (wlOnVideoViewListener != null) {
                            if (!isMove) {
                                if (offset_move_y > 0) {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength);
                                } else {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength) * -1;
                                }
                                wlOnVideoViewListener.moveLeft(-move_offset_percent, MOVE_START);
                            } else {
                                move_offset_percent = (offset_move_y - startMoveOffsetLength) / (getHeight() / 2.0);
                                wlOnVideoViewListener.moveLeft(-move_offset_percent, MOVE_ING);
                            }
                        }
                    } else {
                        if (wlOnVideoViewListener != null) {
                            if (!isMove) {
                                if (offset_move_y > 0) {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength);
                                } else {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength) * -1;
                                }
                                wlOnVideoViewListener.moveRight(-move_offset_percent, MOVE_START);
                            } else {
                                move_offset_percent = (offset_move_y - startMoveOffsetLength) / (getHeight() / 2.0);
                                wlOnVideoViewListener.moveRight(-move_offset_percent, MOVE_ING);
                            }
                        }
                    }
                    isMove = true;
                } else if (type == MOVE_X) {
                    if (wlOnVideoViewListener != null) {
                        if (wlPlayer != null && wlPlayer.getDuration() > 0) {
                            if (!isMove) {
                                if (offset_move_x > 0) {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength) * -1;
                                } else {
                                    startMoveOffsetLength = Math.abs(startMoveOffsetLength);
                                }
                                wlOnVideoViewListener.moveX(seek_time, MOVE_START);
                            } else {
                                move_offset_percent = (offset_move_x + startMoveOffsetLength) / (getWidth() * 3.0);
                                seek_time = wlPlayer.getCurrentTime() + move_offset_percent * wlPlayer.getDuration();
                                if (seek_time < 0) {
                                    seek_time = 0;
                                }
                                if (seek_time > wlPlayer.getDuration()) {
                                    seek_time = wlPlayer.getDuration();
                                }
                                isXMoving = true;
                                wlOnVideoViewListener.moveX(seek_time, MOVE_ING);
                            }
                        }
                    }
                    isMove = true;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isMove) {
                    if (type == MOVE_X) {
                        if (wlOnVideoViewListener != null) {
                            if (seek_time < 0) {
                                seek_time = 0;
                            }
                            if (seek_time > wlPlayer.getDuration()) {
                                seek_time = wlPlayer.getDuration();
                            }
                            if (isXMoving) {
                                wlOnVideoViewListener.moveX(seek_time, MOVE_STOP);
                            } else {
                                wlOnVideoViewListener.moveX(-1, MOVE_STOP);
                            }
                            seek_time = 0;
                        }
                    } else if (type == MOVE_Y) {
                        if (x_down < getWidth() / 2.0) {
                            if (wlOnVideoViewListener != null) {
                                wlOnVideoViewListener.moveLeft(-move_offset_percent, MOVE_STOP);
                            }
                        } else {
                            if (wlOnVideoViewListener != null) {
                                wlOnVideoViewListener.moveRight(-move_offset_percent, MOVE_STOP);
                            }
                        }
                    }
                } else {
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
            if (clickCount == 1) {
                if (wlOnVideoViewListener != null) {
                    wlOnVideoViewListener.onSingleClick();
                }
            } else if (clickCount == 2) {
                if (wlOnVideoViewListener != null) {
                    wlOnVideoViewListener.onDoubleClick();
                }
            }
            clickCount = 0;
            handler.removeCallbacksAndMessages(null);
        }
    };

    public void release() {
        if (surfaceTextur != null) {
            surfaceTextur.release();
            surfaceTextur = null;
        }
        if (surface != null) {
            surface.release();
            surface = null;
        }
        if (wlPlayer != null) {
            wlPlayer = null;
        }
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
