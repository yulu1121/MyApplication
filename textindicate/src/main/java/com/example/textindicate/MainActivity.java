package com.example.textindicate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_NAME = "ACTION";
    private LocalBroadcastManager manager;
    private TextView textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NAME);
        manager.registerReceiver(receiver,intentFilter);
        initView();
    }

    private void initView() {
        textName = (TextView)findViewById(R.id.textView);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_NAME.equals(intent.getAction())){
                String name = intent.getStringExtra("name");
                textName.setText(name);
            }
        }
    };
}
