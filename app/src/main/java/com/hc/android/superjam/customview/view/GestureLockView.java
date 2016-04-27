package com.hc.android.superjam.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by 99165 on 2016/4/20.
 */
public class GestureLockView extends View {


    private static final String TAG = "GestureLockView";

    /**
     * GestureLockView的三种状态
     */
    enum Mode {
        STATUS_NO_FINGER, STATUS_FINGER_ON, STATUS_FINGER_UP;
    }

    /**
     * 当前状态
     */
    private Mode mCruuentStatus = Mode.STATUS_NO_FINGER;

    //宽度
    private int mWidth;

    //高度
    private int mHeight;

    //外圆半径
    private int mRadius;

    //画笔宽度
    private int mStokenWidth = 2;

    //圆心坐标
    private int mCenterX;
    private int mCenterY;
    private Paint mPaint;

    //箭头 山角形最长边的一半 = mArrowRate * mWidth/2
    private float mArrowRate = 0.33f;
    private int mArrowDegree = -1;
    private Path mArrowPath;

    //内圆半径 = mInnerCricleRadiusRate * mRadius
    private float mInnerCricleRadiusRate = 0.3f;

    //四个颜色可以由用户自定义
    private int mColorNoFingerInner;
    private int mColorNoFingerOuter;
    private int mColorFingerOn;
    private int mColorFingerUp;


    public GestureLockView(Context context, int mColorNoFingerInner, int mColorNoFingerOuter, int mColorFingerOn, int mColorFingerUp) {
        super(context);
        this.mColorNoFingerInner = mColorNoFingerInner;
        this.mColorNoFingerOuter = mColorNoFingerOuter;
        this.mColorFingerOn = mColorFingerOn;
        this.mColorFingerUp = mColorFingerUp;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        mArrowPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        //取长和宽中 的最小值
        mWidth = mWidth < mHeight ? mWidth : mHeight;
        mRadius = mCenterX = mCenterY = mWidth / 2;
        mRadius -= mStokenWidth / 2;

        //绘制三角形
        float mArrowLenth = mWidth / 2 * mArrowRate;
        mArrowPath.moveTo(mWidth / 2, mStokenWidth + 2);
        mArrowPath.lineTo(mWidth / 2 - mArrowLenth, mStokenWidth + 2 + mArrowLenth);
        mArrowPath.lineTo(mWidth / 2 + mArrowLenth, mStokenWidth + 2 + mArrowLenth);
        mArrowPath.close();
        mArrowPath.setFillType(Path.FillType.WINDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mCruuentStatus) {
            case STATUS_FINGER_ON:
                //绘制外圆
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorNoFingerOuter);
                mPaint.setStrokeWidth(mStokenWidth);
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        }
    }
}
