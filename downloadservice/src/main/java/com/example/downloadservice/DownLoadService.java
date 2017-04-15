package com.example.downloadservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * Created by Administrator on 2017/1/5.
 */

public class DownLoadService extends Service {
    public static final String DOWN_DIR =
            Environment.getExternalStorageDirectory()+ File.separator+"downloads";
    private LocalBroadcastManager manager;
    private DownLoadTask task;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String filepath = intent.getStringExtra("file_path");
        String fileName = intent.getStringExtra("file_name");
        if(!TextUtils.isEmpty(filepath)&&!TextUtils.isEmpty(fileName)){
           if(task==null){
               task = new DownLoadTask();
               task.execute(filepath,fileName);}
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class DownLoadTask extends AsyncTask<String,Integer,File>{
        @Override
        protected void onPostExecute(File file) {
            Intent intent = new Intent(MainActivity.ACTION_FINISH);
            intent.putExtra("file_path",file.getAbsolutePath());
            manager.sendBroadcast(intent);
            task = null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Intent intent = new Intent(MainActivity.ACTION_PROGRESS);
            intent.putExtra("progress",values[0]);
            manager.sendBroadcast(intent);
        }

        @Override
        protected File doInBackground(String[] params) {
            //创建下载目录
            File dir = new File(DOWN_DIR);
            if(!dir.exists()){
                //创建文件
                dir.mkdir();
            }
            //截取文件名
            String fileName = params[1];
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                URL url = new URL(params[0]);
                try {
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(4000);
                  if(conn.getResponseCode()==200){
                      inputStream = conn.getInputStream();
                      int length = 0;
                      byte[] buf = new byte[1024];
                      File file = new File(dir,fileName);
                      outputStream = new FileOutputStream(file);
                      int len = conn.getContentLength();
                      int total = 0;
                      while((length=inputStream.read(buf))!=-1){
                          outputStream.write(buf,0,length);
                          total+=length;
                          publishProgress((int)(((float)total/len)*100));
                      }
                      return  file;
                  }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(outputStream!=null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }

    }
}
