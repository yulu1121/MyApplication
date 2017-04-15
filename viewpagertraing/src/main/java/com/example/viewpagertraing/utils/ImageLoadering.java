package com.example.viewpagertraing.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by Administrator on 2017/1/6.
 */

public class ImageLoadering {
    //有一个公有的接口
    public interface  ImageListener{
        void imageComplete(Bitmap bitmap);
    }
    //有接口对象
    private ImageListener imageListener;
    //有公有获取资源的方法
    public  void loadImage(String url,ImageListener imageListener){
        this.imageListener = imageListener;
        new ImageAsynTask().execute(url);
    }
    class ImageAsynTask extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(null!=imageListener){
                imageListener.imageComplete(bitmap);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL urling = new URL(params[0]);
                HttpURLConnection conn= (HttpURLConnection) urling.openConnection();
                //输入流获取图片
                InputStream inputStream = conn.getInputStream();
                BufferedInputStream bis= new BufferedInputStream(inputStream);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                inputStream.close();
                bis.close();
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
