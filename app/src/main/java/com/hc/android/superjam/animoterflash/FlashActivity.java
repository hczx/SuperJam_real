package com.hc.android.superjam.animoterflash;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import com.hc.android.superjam.MainActivity;
import com.hc.android.superjam.R;
import com.hc.android.superjam.widget.LogUtil;
import com.hc.android.superjam.widget.ScreenUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 99165 on 2016/4/11.
 */
public class FlashActivity extends AppCompatActivity {

    @Bind(R.id.flash_start_button)
    CardView flashStartButton;

    @Bind(R.id.root_id)
    CoordinatorLayout rootLayout;

    private Animator mStartAnimator;

    int[] location = new int[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        ButterKnife.bind(this);

//        int[] location = new  int[2] ;
//        view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
//        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
//        location [0]--->x坐标,location [1]--->y坐标
        flashStartButton.getLocationOnScreen(location);
        LogUtil.getInstance().debug("x:" + location[0]);
        LogUtil.getInstance().debug("y:" + location[1]);
        LogUtil.getInstance().debug("getRadius:" + flashStartButton.getRadius());
        LogUtil.getInstance().debug("getX:" + flashStartButton.getX());
        LogUtil.getInstance().debug("getPivotX:" + flashStartButton.getPivotX());
        LogUtil.getInstance().debug("getRotationX:" + flashStartButton.getRotationX());
        LogUtil.getInstance().debug("getScaleX:" + flashStartButton.getScaleX());
        LogUtil.getInstance().debug("getScrollX:" + flashStartButton.getScrollX());
        LogUtil.getInstance().debug("getTranslationX:" + flashStartButton.getTranslationX());
    }

    private void showHint() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.flash_start_button)
    public void onClick() {
        int[] location = new int[2];
        flashStartButton.getLocationOnScreen(location);
        mStartAnimator = ViewAnimationUtils.createCircularReveal(
                rootLayout,
                (int) (location[0] + flashStartButton.getRadius()),
                (int) (location[1] + flashStartButton.getRadius()),
                flashStartButton.getRadius(),
                0
        );
        LogUtil.getInstance().debug("x:" + location[0]);
        LogUtil.getInstance().debug("y:" + location[1]);


        mStartAnimator.addListener(startButtonAnimationListener);
        //添加加速器
        mStartAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mStartAnimator.setDuration(1000);
        mStartAnimator.start();


        LogUtil.getInstance().debug("getRadius:" + flashStartButton.getRadius());
        LogUtil.getInstance().debug("getX:" + flashStartButton.getX());
        LogUtil.getInstance().debug("getPivotX:" + flashStartButton.getPivotX());
        LogUtil.getInstance().debug("getRotationX:" + flashStartButton.getRotationX());
        LogUtil.getInstance().debug("getScaleX:" + flashStartButton.getScaleX());
        LogUtil.getInstance().debug("getScrollX:" + flashStartButton.getScrollX());
        LogUtil.getInstance().debug("getTranslationX:" + flashStartButton.getTranslationX());
        LogUtil.getInstance().debug("getTop:" + flashStartButton.getTop());
    }

    Animator.AnimatorListener startButtonAnimationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            rootLayout.setBackgroundResource(R.color.colorAccent);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            rootLayout.setBackgroundResource(R.color.windowBackground);
            MainActivity.startMainActivity(FlashActivity.this);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
