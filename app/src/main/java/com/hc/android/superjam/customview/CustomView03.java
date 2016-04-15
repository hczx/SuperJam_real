package com.hc.android.superjam.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hc.android.superjam.MainActivity;
import com.hc.android.superjam.R;

/**
 * Created by 99165 on 2016/4/14.
 */
public class CustomView03 extends View {

    /**
     * 第一圈的颜色
     */
    private int mFirstColor;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor;

    /**
     * 圆的宽度
     */
    private int mCircleWidth;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 速度
     */
    private int mSpeed;


    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    public CustomView03(Context context) {
        this(context, null);
    }

    public CustomView03(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView03(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomViewCircle, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomViewCircle_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomViewCircle_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomViewCircle_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomViewCircle_speed:
                    mSpeed = typedArray.getInt(attr, 20);//默认20；
                    break;
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        //绘图线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int center = getWidth() / 2;//获取圆心的x坐标
        int radius = center - mCircleWidth / 2;//半径
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
        mPaint.setAntiAlias(true);//设置消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);//定义圆弧的形状和大小的界限
        if (!isNext) {
            //第一颜射的圈完成，第二颜色跑
            mPaint.setColor(mFirstColor);//设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);//根据进度画圆弧
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }
}
