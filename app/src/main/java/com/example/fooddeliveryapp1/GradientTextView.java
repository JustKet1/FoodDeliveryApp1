package com.example.fooddeliveryapp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class GradientTextView extends TextView {

    private final int colorStartGradient;
    private final int colorEndGradient;

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        colorStartGradient = a.getColor(R.styleable.GradientTextView_colorStartGradient, -2);
        colorEndGradient = a.getColor(R.styleable.GradientTextView_colorEndGradient, -2);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (colorStartGradient != -2 && colorEndGradient != -2)
        {
            getPaint().setShader(
                    new LinearGradient(0, 0, 0, getHeight(), colorStartGradient,
                            colorEndGradient, Shader.TileMode.MIRROR));
        }
        super.onDraw(canvas);
    }

}