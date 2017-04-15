package com.example.downloadservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String URL_DOWNLOAD = "http://sj.img4399.com/game_list/47/com.wepie.snake/wepie.snake.v117518.apk?ref=news";
    public static final String ACTION_PROGRESS = "ACTION";
    public static final String ACTION_FINISH = "FINISH";
    private ProgressBar progressBar;
    private LocalBroadcastManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = LocalBroadcastManager.getInstance(this);
        //注册广播
        IntentFilter intentFilter = new IntentFilter(ACTION_PROGRESS);
        intentFilter.addAction(ACTION_FINISH);
        manager.registerReceiver(receiver,intentFilter);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接受更新进度
            if(ACTION_PROGRESS.equals(intent.getAction())){
                int progress = intent.getIntExtra("progress",0);
                progressBar.setProgress(progress);
            }else if(ACTION_FINISH.equals(intent.getAction())){
                Toast.makeText(context, "文件下载完成，保存在"+intent.getStringExtra("file_path"), Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void onClickDownLoad(View view) {
        //将下载的文件和文件名发送给service
        Intent intent = new Intent(this,DownLoadService.class);
        intent.putExtra("file_path",URL_DOWNLOAD);
        intent.putExtra("file_name","snake_apk");
        startService(intent);
    }
}
