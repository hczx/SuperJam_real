package com.hc.android.superjam.customview;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Printer;
import android.util.TypedValue;
import android.view.View;

import com.hc.android.superjam.R;
import com.hc.android.superjam.widget.LogUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Jam on 2016/4/14.
 */
public class CustomView01 extends View {


    /**
     * 文本
     */
    private String mTitleText;

    /**
     * 文本的颜色
     */
    private int mTitleTextColor;

    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;

    /**
     * 画笔
     */
    private Paint mPaint;

    //在View的构造函数里获得我们自定义的属性

    public CustomView01(Context context) {
        this(context, null);
    }

    public CustomView01(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 获得我们所第一的自定义属性
         */
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {

            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.CustomTitleView_mtitleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.CustomTitleView_mtitleTextColor:
                    mTitleTextColor = typedArray.getColor(i, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_mtitleTextSize:
                    //默认设置为16sp,TypeValue也可以把Sp转化为px;
                    mTitleTextSize = typedArray.getDimensionPixelSize(i, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }

        typedArray.recycle();

        /**
         * 获得绘制文本的宽高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);

        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
                postInvalidate();//通知控件
            }
        });
    }

    private String randomText() {

        Random random = new Random();
        Set<Integer> integers = new HashSet<>();
        while (integers.size() < 4) {
            int randomInt = random.nextInt(10);
            integers.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : integers) {
            sb.append("" + i);
        }

        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //EXACTLY 设置了明确的值 或  MATCH_PARENT
        //AT_MOST 表示一个布局限制在一个最大值内  WRAP_CONTENT
        //UNSPECIFIED 表示子布局想要多大  就有多大

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int derired = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
            height = derired;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.getInstance().debug("Paint", mPaint.toString());
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
