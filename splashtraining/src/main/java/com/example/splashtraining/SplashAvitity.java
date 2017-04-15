package com.example.splashtraining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 *
 * Created by Administrator on 2016/12/30.
 */

public class SplashAvitity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Handler handler = new Handler();
        SharedPreferences shared = getSharedPreferences("shared",MODE_PRIVATE);
       boolean flag=shared.getBoolean("first_start",false);
        if(flag){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashAvitity.this,MainActivity.class));
                    finish();
                }
            });
        }else{
            shared.edit().putBoolean("first_start",true).commit();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashAvitity.this,MainActivity.class));
                    finish();
                }
            },6000);
        }
     }
    }

