package com.example.downloadservicetrain;

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

public class MainActivity extends AppCompatActivity {
    public static final String URL_DOWN = "http://sj.img4399.com/game_list/47/com.wepie.snake/wepie.snake.v117518.apk?ref=news";
    //下载的Acition,动态注册广播
    public static final String DOWNLOADPROCESS = "PROCESS";
    public static final String FINISH = "FINISH";
    private LocalBroadcastManager manager;
    private ProgressBar progressBar;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //广播初始化
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(DOWNLOADPROCESS);
        intentFilter.addAction(FINISH);
        manager.registerReceiver(receiver,intentFilter);
        initView();
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(DOWNLOADPROCESS.equals(intent.getAction())){
                int progress = intent.getIntExtra("progress",0);
                progressBar.setProgress(progress);
                textView.setText(progress+"%");
            }else if(FINISH.equals(intent.getAction())) {
                Toast.makeText(context, "文件下载完成，保存在"+intent.getStringExtra("file_path"), Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.text);

    }

    public void onClickDownLoad(View view) {
        //将文件发送给service
        Intent intent = new Intent(this,DownLoadService.class);
        intent.putExtra("file_path",URL_DOWN);
        intent.putExtra("file_name","snake.apk");
        startService(intent);
    }
}
