package com.example.broadcastnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager manager;
    private LocalBroadcastManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = LocalBroadcastManager.getInstance(this);
        manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mManager.registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                if(getNetWorkState()){
                    Log.e("xx","当前网络为"+getNetWorkType());
                }else {
                    Log.e("xx","没有网络");
                }

            }
        }
    };
    private boolean getNetWorkState(){
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected()||info.isConnected();
    }
    /*
    *获得网络名称
    */
    private String getNetWorkType(){
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(null==networkInfo){
            return "没有网络";
        }
        return networkInfo.getTypeName();
    }
}
