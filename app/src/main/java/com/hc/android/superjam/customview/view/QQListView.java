package com.hc.android.superjam.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hc.android.superjam.R;

import java.security.spec.ECField;

/**
 * Created by 99165 on 2016/4/25.
 */
public class QQListView extends ListView {

    private static final String TAG = "QQListView";

    //用户滑动的最小距离
    private int touchSlop;

    //是否响应滑动
    private boolean isSlidding;

    //手指按下时的x坐标
    private int xDown;

    //手指按下时的y做坐标
    private int yDown;

    //手指移动时的x坐标
    private int xMove;

    //手指移动时的y坐标
    private int yMove;

    private LayoutInflater mLayoutInflater;

    private PopupWindow mPopupWindow;
    private int mPopupWindowWidth;
    private int mPopupWindowHeight;

    private Button mDelBtn;

    //手指触摸的当前View
    private View mCurrentView;
    private int mCurrentViewPos;

    /**
     * 进行一些必要的初始化
     *
     * @param context
     * @param attrs
     */
    public QQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutInflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View view = mLayoutInflater.inflate(R.layout.delete_btn, null);
        mDelBtn = (Button) view.findViewById(R.id.deleteBtn);


        mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //先调用一下measure，否则拿不到宽和高
        mPopupWindow.getContentView().measure(0, 0);
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
    }


    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                /**
                 * 如果当前PopWindow显示则直接隐藏
                 */
                if (mPopupWindow.isShowing()) {
                    dismissPopuWindow();
                    return false;
                }
                //获取手指按下时的Item的位置
                mCurrentViewPos = pointToPosition(xDown, yDown);
                //获取手指按下时的Item
                mCurrentView = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                /**
                 * 判断是从右到左的滑动
                 */
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSlidding = true;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return super.onTouchEvent(ev);
        int action = ev.getAction();
        /**
         * 如果是从右到左的滑动才相应
         */
        if (isSlidding) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] loacation = new int[2];
                    //获得当前Item位置的x与y;
                    mCurrentView.getLocationOnScreen(loacation);
                    //设置PopupWindow动画
                    mPopupWindow.update();
                    mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP, loacation[0] + mCurrentView.getWidth(), loacation[1] + mCurrentView.getHeight() / 2 - mPopupWindowHeight / 2);

                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 隐藏PopuWindow
     */
    private void dismissPopuWindow() {
        mPopupWindow.dismiss();
    }
}
