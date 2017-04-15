package com.example.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Handler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mhandler = new Handler();
        imageView = (ImageView) findViewById(R.id.imageAnimation);
        imageView.setImageResource(R.mipmap.adv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置动画
                imageView.setImageResource(R.drawable.donghua);
                //获得animation对象
                AnimationDrawable animation=(AnimationDrawable) imageView.getDrawable();
                //不重复播放
                animation.setOneShot(true);
                //启动动画
                animation.start();
                int time = 0;
                for(int i=0;i<animation.getNumberOfFrames();i++){
                    time+=animation.getDuration(i);
                }
                //调用handler的post delayed方法
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(R.mipmap.open);
                    }
                },time);
            }
        });
    }
}
