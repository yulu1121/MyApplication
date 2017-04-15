package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void  initView(){
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    public void onClickAlpha(View view){
        Animation anim=AnimationUtils.loadAnimation(this,R.anim.alpha);
        imageView.startAnimation(anim);
    }
    public void onClickScale(View view){
        Animation anim=AnimationUtils.loadAnimation(this,R.anim.scale);
        imageView.startAnimation(anim);
    }
    public void onClickRotate(View view){
        Animation anim=AnimationUtils.loadAnimation(this,R.anim.rotate);
        imageView.startAnimation(anim);
    }
    public void onClickTranslate(View view){
        Animation anim=AnimationUtils.loadAnimation(this,R.anim.translate);
        imageView.startAnimation(anim);
    }
}
