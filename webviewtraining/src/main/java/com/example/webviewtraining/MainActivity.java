package com.example.webviewtraining;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //将对象假如到Js中
        webView.loadUrl("file:///android_asset/my_html");
        webView.addJavascriptInterface(new MyJsClass(this),"myJs");
    }

    public void onClickLocal(View view) {
        webView.loadUrl("file:///android_asset/my_html");
    }

    public void onClickNet(View view) {
        webView.loadUrl("http://www.ifeng.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url) {
                view.loadUrl(url);
                return true;
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                dialog.setProgress(newProgress);
                if(newProgress==100){
                    dialog.dismiss();
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                dialog.setTitle(title);
                setTitle(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                //bitmap转为drawble
                Drawable drawableing = new BitmapDrawable(icon);
                dialog.setIcon(drawableing);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickHtml(View view) {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head>");
        html.append("<title>");
        html.append("标题");
        html.append("</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<p>");
        html.append("这是HTML代码创建的网页");
        html.append("</p>");
        html.append("</body>");
        html.append("</html>");
        webView.loadDataWithBaseURL(null,html.toString(),"text/html","utf-8",null);
    }

    public void onClickJS(View view) {
        webView.loadUrl("file:///android_asset/my_html");
        webView.loadUrl("javascript:sayHello()");
    }
}
