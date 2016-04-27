package com.hc.android.superjam.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.hc.android.superjam.R;

/**
 * Created by 99165 on 2016/4/25.
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private static final String TAG = "ArcMenu";

    //菜单显示的位置
    private Position mPosition = Position.LEFT_TOP;

    //菜单显示的半径 默认100dp
    private int mRadius = 100;

    //用户点击的按钮
    private View mButton;

    //当前ArcMenu的状态
    private Status mCurrentStatus = Status.CLOSE;

    //回调接口
    private OnMenuItemClickListener onMenuItemClickListener;


    //状态的枚举
    public enum Status {
        OPEN, CLOSE
    }

    //设置菜单显示的位置，四选1，默认右下
    public enum Position {
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ArcMenu_position:
                    int val = typedArray.getInt(attr, 0);
                    switch (val) {
                        case 0:
                            mPosition = Position.LEFT_TOP;
                            break;
                        case 1:
                            mPosition = Position.RIGHT_TOP;
                            break;
                        case 2:
                            mPosition = Position.RIGHT_BOTTOM;
                            break;
                        case 3:
                            mPosition = Position.LEFT_BOTTOM;
                            break;
                    }
                    break;
                case R.styleable.ArcMenu_radius:
                    mRadius = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 100f, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //mesure child
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutButton();
            int count = getChildCount();
            /**
             * 设置所有孩子的位置 例如（第一个为按钮）：
             * 左上时，从左到右 ，第二个：mRadius(sin0,cos0)
             * 第三个：mRadis(sina,cosa)  注:[a = Math.PI / 2 * (cCount -1)]
             * 第四个：mRadius(sin2a,cos2a)
             */

            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(VISIBLE);
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
                //childview width
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                //右上右下
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }

                child.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    /**
     * 第一个元素为按钮，为按钮布局且初始化点击事件
     */
    private void layoutButton() {
        View cButton = getChildAt(0);
        cButton.setOnClickListener(this);
        int l = 0;
        int t = 0;
        int width = cButton.getMeasuredWidth();
        int height = cButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        cButton.layout(l, t, l + width, t + height);
    }

    @Override
    public void onClick(View v) {
        if (mButton == null) {
            mButton = getChildAt(0);
        }
        rotateView(mButton, 0f, 270f, 300);
        toggleMenu(300);
    }

    private void toggleMenu(int i) {

        int count = getChildCount();

    }

    private void rotateView(View view, float fromDegrees, float toDegree, int durationMillis) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
    }

    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }
}
