package com.example.servicetoactivity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 *
 * Created by Administrator on 2017/1/5.
 */

public class MyMusicService extends Service {
    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    class  MyBinder extends Binder{
        public MyMusicService getService(){
            return MyMusicService.this;
        }
    }
    public void playMusic(String path){
        if(mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
            //重置播放器
            mediaPlayer.reset();
            //设置资源文件
            try {
                mediaPlayer.setDataSource(path);
                //异步缓存
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
                //添加完成监听
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stop();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void stop(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
    public void pause(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
    public void resume(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }
}
