package com.example.downloadcotinue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.downloadcotinue.enties.DownLoadObj;
import com.example.downloadcotinue.services.DownLoadService;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    public static final String FILE_URL = "http://filelx.liqucn.com/upload/2016/qipai/ofm-777-101-v2.13.6.apk";
    public static final String FILE_NAME = "apple.apk";
    public static final String DOWNLOAD_ACTION = "downloading";
    public static final String DOWNLOAD_COMPLETE = "complete";
    private LocalBroadcastManager manager;
    private ProgressBar progressBar;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DOWNLOAD_ACTION);
        intentFilter.addAction(DOWNLOAD_COMPLETE);
        manager.registerReceiver(receiver,intentFilter);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.progressNumber);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
          DownLoadObj obj = (DownLoadObj) intent.getSerializableExtra("obj");
            if(DOWNLOAD_ACTION.equals(action)){
                progressBar.setProgress((int) obj.getProgress());
                progressBar.setMax((int) obj.getLength());
                textView.setText(""+obj.getProgress());
            }else if(DOWNLOAD_COMPLETE.equals(action)){
                progressBar.setProgress(0);
                Toast.makeText(context,obj.getFileName()+ "下载完成", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void onClickStart(View view) {
        Intent intent = new Intent(this, DownLoadService.class);
        intent.putExtra("url",FILE_URL);
        intent.putExtra("cmd",DownLoadService.START_CMD);
        intent.putExtra("fileName",FILE_NAME);
        startService(intent);
    }

    public void onClickStop(View view) {
        Intent intent = new Intent(this,DownLoadService.class);
        intent.putExtra("url",FILE_URL);
        intent.putExtra("cmd",DownLoadService.STOP_CMD);
        startService(intent);
    }
}
