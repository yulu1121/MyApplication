package com.example.loginmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EditText mUserName;
    private EditText mUserPass;
    private Retrofit retrofit;
    private AppService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPass = (EditText) findViewById(R.id.user_pass);
        retrofit=new Retrofit.Builder().
                 baseUrl("http://192.168.53.6:8080/AndroidServelet/")
                .build();
        service=retrofit.create(AppService.class);
    }

    public void submit(View view) {
        service.login(mUserName.getText().toString(),mUserPass.getText().toString(),"ANDROID")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String cookie = response.headers().get("set-cookie");
                        Log.e("xxx",cookie);
                        try {
                            String result = response.body().string();
                            Log.e("xxx",result);
                            if("success".equals(result)){
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
