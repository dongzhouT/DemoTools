package com.tao.demo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.tao.demo.R;

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
    int sizeOffset = 10;//画布尺寸偏移量
    float angleOffset = 3.22f;//角度偏移量
    boolean enabled = true, isMoving;
    private double settingTemp = 0;//设置温度
    int tempNum = 28;//16~30度，温度间隔
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
        double d = 180 * 100 / (tempNum * 2);
        //计算水滴角度
        angleOffset = Math.round(d) / 100.0f;
        //设置背景图片
        setImageResource(R.drawable.ic_temp_bg);
        setBackgroundResource(R.drawable.ic_temp_blue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(rectF.width() / 2, getHeight());
        path.addArc(rectF, 0f, (float) sweepAngle);
        path.lineTo(rectF.width() / 2, getHeight());
        path.close();
        canvas.clipPath(path);
        //画图辅助,调试时打开
//        canvas.drawPath(path, mPaint);
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

                double angle1 = sweepAngle;
                //越界处理
                if (y > radius && x < radius) {
                    sweepAngle = -180;
                } else if (y > radius && x > radius) {
                    sweepAngle = 0;
                } else {
                    sweepAngle = sweepAngle < 0 ? 0 : -sweepAngle;
                }
                double tempDt = Math.round((Math.abs(sweepAngle) * tempNum / 180)) / 2.0f;
                settingTemp = 30 - tempDt;
                Log.e(TAG, "sweep=" + sweepAngle + ",temp=" + settingTemp + ",dt=" + tempDt);
//                Log.e("event", "============:" + "x:" + (x - radius) + ",Y:" + (radius - y) + ",sweepAngle:" + Math.tosweepAngles(Math.atan2(x - radius, radius - y)));
                if (changeListener != null) {
//                    changeListener.onTempChange((float) settingTemp);
                    changeListener.onTempChange(settingTemp + ",angle1=" + angle1 + ",sweep=" + sweepAngle + ",dt=" + tempDt);
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
        calcAngle();
        invalidate();
    }

    /**
     * 初始化温度，带动画
     *
     * @param temp
     */
    public void setTempWithAnim(double temp) {
        settingTemp = temp;
        startAnimation(16.0f);
    }

    /**
     * 从当前温度调节到设定温度
     *
     * @param dstTemp 调节温度
     */
    public void setTempTo(double dstTemp) {
        setTemp(settingTemp);
        settingTemp = dstTemp;
        startAnimation(settingTemp);
    }

    private void calcAngle() {
        sweepAngle = (settingTemp - 16) * 360 / tempNum - 180 * (28 / tempNum) + angleOffset;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            this.setImageResource(R.drawable.ic_temp_blue);
        } else {
            setTemp(30);
            this.setImageResource(R.drawable.ic_temp_offline);
        }
    }

    private void startAnimation(double startTemp) {
        ValueAnimator anim = ValueAnimator.ofFloat((float) startTemp, (float) settingTemp);
        anim.setDuration(2000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                settingTemp = (float) animation.getAnimatedValue();
                setTemp(settingTemp);
            }
        });
        anim.start();
    }

    public interface OnTempChangeListener {
        void onTempChange(String temp);
    }


}
