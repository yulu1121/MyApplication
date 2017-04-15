package com.example.mvpmodetraning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mvpmodetraning.presenter.IMainPresenter;
import com.example.mvpmodetraning.presenter.impl.IMainPresenterImpl;

/**
 * MVP模式,P是View和Mode之间的桥梁
 */
public class MainActivity extends AppCompatActivity implements IMainPresenter.IPresenterCallBack {
    private TextView textView;
    private IMainPresenter mainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.main_text);
        mainPresenter = new IMainPresenterImpl(this);
        mainPresenter.querList();
    }

    @Override
    public void refreshView(String data) {
        textView.setText(data);
    }
}
