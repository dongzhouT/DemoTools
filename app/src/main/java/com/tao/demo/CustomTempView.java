package com.tao.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.LruCache;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ScrollView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2018/8/1.
 */

public class CustomTempView extends View {
    Paint tickPaint;
    private int tickLength, tickCount = 80;
    private int width, height;
    private float mAngle;
    ArrayList<Integer> colors = new ArrayList<>();
    private double settingTemp = 0;
    private int defaultColor;
    float tick = 0;
    private float radius;
    OnTempChangeListener changeListener;
    private boolean enabled = true;
    ScrollView mScrollView;

    public void setmScrollView(ScrollView mScrollView) {
        this.mScrollView = mScrollView;
    }

    public void setOnTempChangeListener(OnTempChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CustomTempView(Context context) {
        super(context);
    }

    public CustomTempView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        tickPaint = new Paint();
        tickPaint.setAntiAlias(true);
        tickPaint.setStrokeWidth(dip2px(getContext(), 3));
        tickPaint.setColor(Color.parseColor("#55B6DF"));
        tickLength = dip2px(getContext(), 15);
        mAngle = 360f / tickCount;
        colors.add(Color.parseColor("#55B6DF"));
        colors.add(Color.parseColor("#42BAED"));
        colors.add(Color.parseColor("#57ABE4"));
        colors.add(Color.parseColor("#6CA2DE"));
        colors.add(Color.parseColor("#8A94D5"));
        colors.add(Color.parseColor("#8994D5"));
        colors.add(Color.parseColor("#B694D0"));
        colors.add(Color.parseColor("#D47DC4"));
        colors.add(Color.parseColor("#CF80C6"));
        colors.add(Color.parseColor("#C083C9"));
        defaultColor = Color.parseColor("#F1F1F1");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawticks(canvas);
    }

    private void drawticks(Canvas canvas) {
        for (int i = 0; i < tickCount; i++) {
            float temp = (i / 80f) * 14 + 16;
            if (temp <= settingTemp) {
                int colorIndex = (int) (i / 80f * colors.size());
                tickPaint.setColor(colors.get(colorIndex));
            } else {
                tickPaint.setColor(defaultColor);
            }
            canvas.drawLine(width / 2, 0, width / 2, tickLength, tickPaint);
            canvas.rotate(mAngle, width / 2, height / 2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MeasureSpec.getMode(widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = width / 2;
    }

    //设置温度
    public void setTemp(double temp) {
        settingTemp = temp;
        invalidate();
    }

    public void initTemp(int temp) {
        settingTemp = temp;
        startAnimation();
    }

    public void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofFloat(16.0f, (float) settingTemp);
        anim.setDuration(2000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                settingTemp = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    double degree;
    boolean isMoving;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!enabled) {
            return true;
        }
        float x = event.getX(), y = event.getY();
        if ((radius - tickLength * 2) > Math.sqrt(Math.pow(x - radius, 2) + Math.pow(y - radius, 2)) && !isMoving) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                degree = Math.toDegrees(Math.atan2(x - radius, radius - y));
                degree = degree >= 0 ? degree : 360 + degree;
//                settingTemp = Math.round((degree / 360) * 14 + 16);//滑动加一度放开此行
                settingTemp = Math.round((degree / 360) * 28 + 32) / 2.0f;//滑动增加0.5度
//                Log.e("event", "============:" + "x:" + (x - radius) + ",Y:" + (radius - y) + ",degree:" + Math.toDegrees(Math.atan2(x - radius, radius - y)));
                if (changeListener != null) {
                    changeListener.onTempChange((float) settingTemp);
                }
                setTemp(settingTemp);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isMoving = false;
                break;
        }
//        if (mScrollView != null) {
//            mScrollView.requestDisallowInterceptTouchEvent(true);
//        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
    }


    public interface OnTempChangeListener {
        void onTempChange(float temp);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
