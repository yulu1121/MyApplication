package com.example.okhttpdemo.application;

import android.app.Application;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 *
 * Created by Administrator on 2017/2/6.
 */

public class MyApplication extends Application {
    private static OkHttpClient.Builder okHttpClient ;
    private  OkHttpClient cilent;
    private Interceptor interceptor;
    private Cache cache;
    @Override
    public void onCreate() {
        super.onCreate();
        bulidOkHttpClient();
    }
    public static OkHttpClient.Builder bulidOkHttpClient(){
        if(null==okHttpClient){
            return new OkHttpClient.Builder();
        }
        return okHttpClient;
    }
    public  OkHttpClient bulidOkHttpClient(Interceptor interceptor,Cache cache){
        if(null==cilent){
            return new OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build();
        }
        return cilent;
    }
}
