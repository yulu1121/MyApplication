package com.example.okhttpdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.okhttpdemo.application.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private MyHandler myHandler;
    private OkHttpClient.Builder client;
    private ImageView imageView;
    CacheIntercepter intercepter;
    Cache cache;
    File cacheDir;
    //最大缓存
    int maxSize = 4*1024*1024;
    public static final int REQUESTCODE = 1;
    public static final String URLDOWLOAD = "http://www.1688wan.com/majax.action?method=getWebfutureTest";
    public static final String URLDOWNLOAD_POST="http://www.1688wan.com/majax.action?method=getGiftList&";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHandler = new MyHandler(this);
        cacheDir = getCacheDir();
        cache = new Cache(cacheDir,maxSize);
        intercepter = new CacheIntercepter();
        client = MyApplication.bulidOkHttpClient();
        textView = (TextView) findViewById(R.id.downLoad_textView);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    public void onClickDownLoad(View view) {
        //创建request对象
        Request request = new Request.Builder()
                .url(URLDOWLOAD)
                .get()
                .build();
        OkHttpClient build = client.build();
        Call call = build.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("xxx","failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message = myHandler.obtainMessage();
                message.obj = string;
                message.sendToTarget();
            }
        });
    }

    public void onClickPost(View view) {
        FormBody body = new FormBody.Builder()
                        .add("pageno","1")
                        .build();
        Request request = new Request.Builder()
                        .url(URLDOWNLOAD_POST)
                        .post(body)
                        .build();
        OkHttpClient build = client.build();
        Call call = build.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message = myHandler.obtainMessage();
                message.obj = string;
                message.sendToTarget();
            }
        });
    }
    //打开相册
    public void onClickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(data.getData());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len=inputStream.read(buf))!=-1){
                baos.write(buf,0,len);
            }
            baos.flush();
            byte[] bytes = baos.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //使用缓存
    public void onClickCache(View view) {
        Request request = new Request.Builder()
                        .url(URLDOWLOAD)
                        .cacheControl(new CacheControl.Builder().maxAge(3600, TimeUnit.SECONDS).build())
                        .build();
        OkHttpClient build = client.addNetworkInterceptor(intercepter)
                .cache(cache)
                .build();
         build.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("xxx",string);

            }
        });
    }

    static class MyHandler extends Handler{
        private WeakReference<MainActivity> weak;
        MyHandler(MainActivity activity){
            weak = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = weak.get();
            //做空指针判断
            try {
                if(null!=mainActivity){
                    mainActivity.textView.setText(msg.obj.toString());
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}
