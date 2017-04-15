package com.example.progressbarnotify;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private NotificationManager manager;
    private Handler mhandler;
    private int mProgress = 0;
    public static final int CODE_PROGRESS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mhandler = new Handler();
    }

    public void onClickProgress(View view) {
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                //使用远程视图加载布局
                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.progress);
                //设置进度值
                remoteViews.setInt(R.id.progress_bar,"setProgress",mProgress);
                remoteViews.setTextViewText(R.id.progress_tv,"当前进度"+mProgress+"%");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                Notification build = builder.setContentTitle("正在下载")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContent(remoteViews)
                        .setContentText("当前进度")
                        .build();
                manager.notify(CODE_PROGRESS,build);
                if(mProgress>=100){
                    manager.cancel(CODE_PROGRESS);
                }else {
                    mProgress+=10;
                    mhandler.postDelayed(this,1000);
                }
            }
        });
    }
}
