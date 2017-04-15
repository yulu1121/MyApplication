package com.example.musicclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.musicservice.aidl.IMyAidlInterface;


public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface myAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("MusicService");
        intent.setPackage("com.example.musicservice");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
                myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlInterface = null;
        }
    };
    public void onClickPlay(View view) {
        try {
            myAidlInterface.play("storage/sdcard0/music/JS-杀破狼.mp3");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickPause(View view) {
        try {
            myAidlInterface.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickResume(View view) {
        try {
            myAidlInterface.resume();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickStop(View view) {
        try {
            myAidlInterface.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
