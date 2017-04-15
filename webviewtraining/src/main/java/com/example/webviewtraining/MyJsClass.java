package com.example.webviewtraining;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * 用于测试Js的类
 * Created by Administrator on 2017/1/12.
 */

public class MyJsClass {
    private Context context;
    public MyJsClass(Context context){
        this.context = context;
    }
    //显示Toast
    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
