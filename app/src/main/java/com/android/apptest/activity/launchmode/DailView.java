package com.android.apptest.activity.launchmode;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by zhoujian on 2017/2/9.
 */

public class DailView extends ImageView {


    /* 单例模式 */
    public static DailView floatView;
    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    @SuppressWarnings("unused")
    private float mStartX;
    @SuppressWarnings("unused")
    private float mStartY;
    private View.OnClickListener mClickListener;
    private int offset = 0;
    private Handler handler = new Handler();
    private long startTime;
    private WindowManager windowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams();

    public DailView(Context context) {
        super(context);
        setUpWindowParams();
    }

    public DailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpWindowParams();
    }

    public static DailView getFloatView(Context context) {
        if (floatView == null) {
            floatView = new DailView(context.getApplicationContext());
        }
        return floatView;
    }


    public void setUpWindowParams() {
        // 设置window type
        if (Build.VERSION.SDK_INT >= 19) {
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // 设置Window flag
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 注意，flag的值可以为： LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
		 * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦 LayoutParams.FLAG_NOT_TOUCHABLE
		 * 不可触摸
		 */
        // 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        // 以屏幕左上角为原点，设置x、y初始值
        windowManagerParams.x = getPixelFromDip(getResources().getDisplayMetrics(), 10);
        windowManagerParams.y = frame.bottom - getPixelFromDip(getResources().getDisplayMetrics(), 140);
        // 设置悬浮窗口长宽数据
        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
        handler = new Handler();
        fadeIn();
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        handler = null;
    }

    public void fadeIn() {
        if (handler != null) {
            startTime = System.currentTimeMillis();
            handler.postDelayed(new Runnable() {
                public void run() {
                    fadeInHandler(DailView.this, windowManagerParams, startTime);
                }
            }, 100);
        }
    }

    public void fadeInHandler(final View notificationView, final WindowManager.LayoutParams params, final long startTime) {
        try {
            if (handler != null) {
                long timeNow = System.currentTimeMillis();
                float alpha;
                if (timeNow - startTime < 1000.0f) {
                    alpha = 0.6f + ((timeNow - startTime) / 1000.0f) * 0.4f;
                } else {
                    alpha = 1.0f - ((timeNow - startTime - 1000.f) / 1000.0f) * 0.4f;
                }
                params.alpha = Math.abs(alpha);
                windowManager.updateViewLayout(notificationView, params);
                if (timeNow - startTime <= 2000) {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            fadeInHandler(notificationView, params, startTime);
                        }
                    }, 100);
                } else {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            fadeIn();
                        }
                    }, 100);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
//			handler=null;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取到状态栏的高度
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchX = event.getX();
                mTouchY = event.getY();
                mStartX = x;
                mStartY = y;
                break;

            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                updateViewPosition(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                updateViewPosition(event.getX(), event.getY());
                mTouchX = mTouchY = 0;
                if (offset <= Math.sqrt(Math.pow(getMeasuredHeight(), 2) + Math.pow(getMeasuredWidth(), 2)) / 2 && mClickListener != null) {
                    mClickListener.onClick(this);
                }
                offset = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                offset = 0;
                break;
        }
        return true;
    }

    @Override
    public void setOnClickListener(View.OnClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition(float offX, float offY) {
        // 更新浮动窗口位置参数
        offset += Math.sqrt(Math.pow(mTouchX - offX, 2) + Math.pow(mTouchY - offY, 2));
        mTouchX = offX;
        mTouchY = offY;
        // windowManagerParams.x = (int) (x - mTouchX);
        // windowManagerParams.y = (int) (y - mTouchY);
        // windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }

    public WindowManager.LayoutParams getWindowManagerParams() {
        return windowManagerParams;
    }

    public void setWindowManagerParams(WindowManager.LayoutParams windowManagerParams) {
        this.windowManagerParams = windowManagerParams;
    }


    public int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }



}
