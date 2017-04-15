package com.example.frgment_activity_communication;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MyFragment.IFragment2Activity{

    private FragmentManager supportFragmentManager;
    private MyFragment myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportFragmentManager = getSupportFragmentManager();
    }
    //setArguments传值只能在Fragment初始化的时候进行
    public void a2f(View view) {
        myFragment = MyFragment.newInstance("this is Activity");
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_layout,myFragment);
        transaction.commit();
    }
    //初始化完成后的传值方式
    public void a2f2(View view) {
        myFragment.setValue("this is ActivityAfter");
    }

    @Override
    public void setActivityValue(String value) {
        Log.e("xxx",value);
    }
}
