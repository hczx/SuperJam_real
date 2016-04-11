package com.hc.android.superjam;

import android.accounts.Account;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by Jam on 2016/3/14.
 */
public abstract class BaseApplication extends Application {


    private static volatile Context sAppContent;
    private static volatile BaseApplication mInstance;
    private static volatile Handler sAppHandler;


    /**
     * 根据 account json 返回 account
     *
     * @param json json value
     * @return Account
     */
    public abstract Account accountFromJson(String json);


    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        Fresco.initialize(appContext());
    }

    private void initialize() {
        mInstance = this;
        sAppContent = getApplicationContext();
        sAppHandler = new Handler(sAppContent.getMainLooper());
    }


    public static Context appContext() {
        return sAppContent;
    }

    public static Resources appResources() {
        return appContext().getResources();
    }

    /**
     * application handler
     */
    public static Handler appHandler() {
        return sAppHandler;
    }

    /**
     * current application instance
     */
    /**
     * @return current application instance
     */
    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sAppContent = null;
        mInstance = null;
        sAppHandler = null;
    }

}
