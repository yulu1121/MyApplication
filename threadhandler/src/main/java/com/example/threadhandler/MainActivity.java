package com.example.threadhandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final int IMAGE_WHAT = 1;
    private Handler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                //在子线程中创建handler
                mhandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what){
                            case IMAGE_WHAT:
                                Log.e("xx",Thread.currentThread().getName()+"收到"+msg.obj);
                                break;
                        }
                    }
                };
                Looper.loop();
            }
        }).start();
    }

    public void onClickMessage(View view) {
        //主线程给子线程发消息
        mhandler.obtainMessage(IMAGE_WHAT,Thread.currentThread().getName())
                .sendToTarget();
    }

    public void onClickThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mhandler.obtainMessage(IMAGE_WHAT,Thread.currentThread().getName()

                ).sendToTarget();
            }
        }).start();
    }
}
