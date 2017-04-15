package com.example.mybroadcasting;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyBroadCast myBroadCast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        IntentFilter filter = new IntentFilter();
//        //动态注册
//        myBroadCast = new MyBroadCast();
//        registerReceiver(myBroadCast,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myBroadCast);
    }

    public void onClickSendMessage(View view) {
        Intent intent = new Intent(myBroadCast.ACTION_MY);
        intent.putExtra("msg","你好，我是余璐");
        sendBroadcast(intent);
    }
}
