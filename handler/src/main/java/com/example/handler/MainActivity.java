package com.example.handler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private final int IMAGE_WHAT = 1;
    public static final String IMAGE_URL = "http://img1.imgtn.bdimg.com/it/u=2288579568,729734179&fm=21&gp=0.jpg";
    private ImageView imageView;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    //处理消息,要重写handleMessage的方法
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case IMAGE_WHAT:
                    //图片显示
                    Bitmap bitMap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitMap);
                    dialog.dismiss();
                    break;
            }
        }
    };
    private void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        dialog = new ProgressDialog(this);
    }
    public void onClickImage(View view){
        dialog.show();
        //启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(IMAGE_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream in = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Message message = mhandler.obtainMessage(IMAGE_WHAT,bitmap);
                    mhandler.sendMessage(message);
//                    mhandler.obtainMessage(IMAGE_WHAT,bitmap).sendToTarget();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
