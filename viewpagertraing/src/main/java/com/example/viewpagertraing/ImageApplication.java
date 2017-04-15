package com.example.viewpagertraing;

import android.app.Application;

import com.example.viewpagertraing.utils.ImageUtils;

/**
 *
 * Created by Administrator on 2017/1/6.
 */

public class ImageApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageUtils.initCache(getApplicationContext());
    }
}
