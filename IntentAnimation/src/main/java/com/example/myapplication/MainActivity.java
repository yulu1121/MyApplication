package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickIntent(View view){
        Intent intent = new Intent(this,NewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.translate_in,R.anim.translate_out);
    }
    public void onClickIntentHeng(View view){
        Intent intent = new Intent(this,NewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.translate_heng_in,R.anim.translate_heng_out);
    }
}
