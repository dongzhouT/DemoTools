package com.tao.demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundImage extends android.support.v7.widget.AppCompatImageView {
    Paint paint;
    float radius;
    int width, height;

    public RoundImage(Context context) {
        super(context);
        init();
    }

    public RoundImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        radius = Math.min(width, height) / 2;
        paint.setStrokeWidth(radius);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xffffffff);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }
}
