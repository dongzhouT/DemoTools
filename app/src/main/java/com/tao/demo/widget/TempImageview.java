package com.tao.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author taodzh
 * create at 2019/6/13
 */
public class TempImageview extends AppCompatImageView {
    private static final String TAG = "==TempImageview==";
    int width, height;
    float radius;
    RectF rectF;
    double sweepAngle = -190;
    int sizeOffset = 10;//偏移量
    float angleOffset = 3.5f;//角度偏移量
    boolean enabled = true, isMoving;
    private double settingTemp = 0;//设置温度
    OnTempChangeListener changeListener;
    Paint mPaint;

    public OnTempChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(OnTempChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public TempImageview(Context context) {
        super(context);
        init();
    }

    public TempImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        Log.e(TAG, "width=" + w + ",height=" + h);
        rectF.left = -sizeOffset;
        rectF.top = -sizeOffset;
        //高度为图片的两倍
        rectF.bottom = h * 2 + sizeOffset;
        rectF.right = w + sizeOffset;
        radius = Math.min(rectF.width(), rectF.height()) / 2;
    }

    void init() {
        rectF = new RectF();
        mPaint = new Paint();
        mPaint.setColor(0xffff0000);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(rectF.width() / 2, getHeight());
        path.addArc(rectF, 0f, (float) sweepAngle);
        path.lineTo(rectF.width() / 2, getHeight());
        path.close();
        canvas.clipPath(path);
        canvas.drawPath(path, mPaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!enabled) {
            return true;
        }
        float x = event.getX(), y = event.getY();
//        if ((radius - tickLength * 2) > Math.sqrt(Math.pow(x - radius, 2) + Math.pow(y - radius, 2)) && !isMoving) {
//            return true;
//        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                sweepAngle = Math.toDegrees(Math.atan2(radius - y, x - radius));
                Log.e(TAG, "r=" + radius + ",x=" + x + ",y=" + y + ",sweepAngle=" + sweepAngle);
                if (y > radius && x < radius) {
                    sweepAngle = -180;
                } else if (y > radius && x > radius) {
                    sweepAngle = 0;
                } else {
                    sweepAngle = sweepAngle < 0 ? 0 : -sweepAngle;
                }
                if (sweepAngle <= 0 && sweepAngle >= -90) {
                    sweepAngle += angleOffset;
                } else if (sweepAngle < -90 && sweepAngle >= -180) {
                    sweepAngle -= angleOffset;
                }
                double aaa = 30 - Math.round((Math.abs(sweepAngle) * 28 / 180)) / 2.0f;
//                settingTemp = Math.round((sweepAngle / 360) * 14 + 16);//滑动加一度放开此行
                settingTemp = Math.round((sweepAngle / 360) * 28 + 32) / 2.0f;//滑动增加0.5度
                settingTemp = aaa;
//                Math.abs(sweepAngle)
                Log.e(TAG, "sweep=" + sweepAngle + ",temp=" + settingTemp);
//                Log.e("event", "============:" + "x:" + (x - radius) + ",Y:" + (radius - y) + ",sweepAngle:" + Math.tosweepAngles(Math.atan2(x - radius, radius - y)));
                if (changeListener != null) {
//                    changeListener.onTempChange((float) settingTemp);
                    changeListener.onTempChange(settingTemp + ",sweep=" + sweepAngle);
                }
                setTemp(settingTemp);
//                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isMoving = false;
                break;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
    }

    //设置温度
    public void setTemp(double temp) {
        settingTemp = temp;
        if (settingTemp > 23) {
            sweepAngle = (settingTemp - 16) * 360 / 28 - 180 + angleOffset;
        } else if (settingTemp == 23) {
            sweepAngle = (settingTemp - 16) * 360 / 28 - 180;
        } else {
            sweepAngle = (settingTemp - 16) * 360 / 28 - 180 - angleOffset;
        }
        invalidate();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public interface OnTempChangeListener {
        void onTempChange(String temp);
    }


}
