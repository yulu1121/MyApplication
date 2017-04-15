package com.example.mybroadcasting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

/**
 *
 * Created by Administrator on 2017/1/4.
 */

public class MyBroadCast extends BroadcastReceiver {
    public static final String ACTION_MY="yulu.haha";
    public static final String ACTION_NET = ConnectivityManager.CONNECTIVITY_ACTION;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(ACTION_MY.equals(action)){
            String msg = intent.getStringExtra("msg");
            Toast.makeText(context, "接受消息"+msg, Toast.LENGTH_SHORT).show();
        }else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            Log.e("xx","网络状态改变");
        }
    }
}
