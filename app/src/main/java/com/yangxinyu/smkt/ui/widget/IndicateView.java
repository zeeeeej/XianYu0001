package com.yangxinyu.smkt.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yangxinyu.smkt.R;

public class IndicateView extends View {
    private int max = 0;
    private int progress = 0;

    private Paint paint;
    private int progressColor;
    private int otherColor;


    public IndicateView(Context context) {
        super(context);
    }

    public IndicateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        otherColor = Color.parseColor("#ffcccccc");
        progressColor = Color.TRANSPARENT;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float w = max * (getContext().getResources().getDimension(R.dimen.indicate_default_line_width));
        float h = getContext().getResources().getDimension(R.dimen.indicate_default_line_height);
        setMeasuredDimension((int) w, (int) h);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float offset = (float) height / 2;
        paint.setStrokeWidth(offset);
        float per = (1f * width - 2 * offset) / (max + 2);
        float positionX = offset;
        for (int i = 0; i < max + 1; i++) {
            boolean isProgress = i == progress + 1;
            paint.setColor(isProgress ? progressColor : otherColor);
            float length = (isProgress ? per * 2 : per);
            float startX = positionX;
            positionX += length;
            float enxX = positionX;
            canvas.drawLine(startX, offset, enxX, offset, paint);
        }
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
