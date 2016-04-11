package com.hc.android.superjam.animoterflash;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import com.hc.android.superjam.R;
import com.hc.android.superjam.widget.ScreenUtils;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        ButterKnife.bind(this);


    }

    private void showHint() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.flash_start_button)
    public void onClick() {
        if (mStartAnimator == null) {
            mStartAnimator = ViewAnimationUtils.createCircularReveal(
                    flashStartButton,
                    ScreenUtils.getScreenHeight() / 2,
                    ScreenUtils.getScreenWidth() / 2,
                    flashStartButton.getRadius(),
                    0
            );
            mStartAnimator.addListener(startButtonAnimationListener);
            //添加加速器
            mStartAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mStartAnimator.setDuration(1000);
            mStartAnimator.start();
        }
    }

    Animator.AnimatorListener startButtonAnimationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

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
