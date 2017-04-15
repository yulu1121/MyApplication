package com.example.sharedisanfang;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 *
 * Created by Administrator on 2017/2/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this);
    }
}
