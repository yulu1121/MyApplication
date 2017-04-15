package com.example.servicetoactivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MyMusicService myMusicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,MyMusicService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
        initView();
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyMusicService.MyBinder binder = (MyMusicService.MyBinder) service;
            myMusicService =  binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                myMusicService=null;
        }
    };
    private void initView() {
        listView = (ListView) findViewById(R.id.list_View);
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA},
                null,null,null
                );
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.listview_item,cursor,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA},
                new int[]{R.id.songName,R.id.singerName,R.id.songPath},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.songPath);
                String path = textView.getText().toString();
                myMusicService.playMusic(path);
            }
        });
    }

    public void onClickStop(View view) {
        myMusicService.stop();
    }

    public void onClickResume(View view) {
        myMusicService.resume();
    }

    public void onClickPause(View view) {
        myMusicService.pause();
    }
}
