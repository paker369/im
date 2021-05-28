package com.haife.app.nobles.spirits.kotlin.app.impl;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.BarUtils;

import timber.log.Timber;

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.i(activity + " - onActivityCreated");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        }
//        String name = activity.getClass().getName();
//        if (name.contains("com.yalantis.ucrop")) {
//            //三方拍照
//        } else if (("com.ynkj.jdb4android.mvp.ui.activity.MainActivity".equals(name)) ||
//                ("com.ynkj.jdb4android.mvp.ui.activity.MineAccountActivity".equals(name))||
//                ("com.ynkj.jdb4android.mvp.ui.activity.DetailsRedEnvelopeActivity".equals(name))) {
//            BarUtils.setStatusBarAlpha(activity, 0, true);
//            BarUtils.setStatusBarLightMode(activity, true);
//        } else {
//            BarUtils.setStatusBarAlpha1(activity, 0, true, true);
//            BarUtils.setStatusBarLightMode(activity, true);
//        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.i(activity + " - onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.i(activity + " - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.i(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.i(activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.i(activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.i(activity + " - onActivityDestroyed");
    }
}
