package com.example.systemdownload;

import android.app.DownloadManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    public static final String DOWNLOAD_URL = "http://sj.img4399.com/game_list/47/com.wepie.snake/wepie.snake.v117518.apk?ref=news";
    private DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

    }

    public void onClickDownLoad(View view) {
        downloadManager.enqueue(new DownloadManager.Request(Uri.parse(DOWNLOAD_URL)));
    }
}
