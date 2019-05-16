package com.tao.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class LinearGradientTextview extends android.support.v7.widget.AppCompatTextView {
    Paint paint;
    LinearGradient linearGradient;
    Matrix matrix;
    float tranX;
    float deltaX = 20;
    float textwidth;

    public LinearGradientTextview(Context context) {
        super(context);
        init();
    }

    public LinearGradientTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        paint = getPaint();
        textwidth = paint.measureText(getText().toString());
        int gradientSize = (int) (3 * textwidth / getText().toString().length());
        linearGradient = new LinearGradient(-gradientSize, 0, 0, 0, new int[]{0x22ffffff, 0xffff00ff, 0x22ffffff},
                new float[]{0f, 0.5f, 1f}, Shader.TileMode.CLAMP);
        matrix = new Matrix();
        paint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tranX += deltaX;
        if (tranX > textwidth + 1 || tranX < 1) {
            deltaX = -deltaX;
        }
        matrix.setTranslate(tranX, 0);
        linearGradient.setLocalMatrix(matrix);

        postInvalidateDelayed(100);
    }
}
