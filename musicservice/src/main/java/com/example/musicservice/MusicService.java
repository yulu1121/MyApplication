package com.example.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.musicservice.aidl.IMyAidlInterface;

import java.io.IOException;

/**
 *
 * Created by Administrator on 2017/1/6.
 */

public class MusicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyPlayer();
    }
    class MyPlayer extends IMyAidlInterface.Stub{
        private MediaPlayer mediaPlayer;
        @Override
        public void play(String path) throws RemoteException {
            if(mediaPlayer==null){
                mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void pause() throws RemoteException {
            if(mediaPlayer!=null){
                mediaPlayer.pause();
            }
        }

        @Override
        public void resume() throws RemoteException {
            if(mediaPlayer!=null){
                mediaPlayer.start();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if(mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
            }
        }

    }
}
