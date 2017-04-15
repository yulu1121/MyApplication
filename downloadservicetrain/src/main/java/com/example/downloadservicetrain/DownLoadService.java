package com.example.downloadservicetrain;

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
 * Created by Administrator on 2017/1/6.
 */

public class DownLoadService extends Service {
    public static final String DOWN_DIR = Environment.getExternalStorageDirectory()
            + File.separator+"downloads";
    private LocalBroadcastManager manager;
    private DownLoadTask task;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取文件
        String fileName = intent.getStringExtra("file_name");
        String filePath = intent.getStringExtra("file_path");
        if(!TextUtils.isEmpty(fileName)&&!TextUtils.isEmpty(filePath)){
            if(task==null){
                task = new DownLoadTask();
                task.execute(filePath,fileName);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    class DownLoadTask extends AsyncTask<String,Integer,File>{
        @Override
        protected void onPostExecute(File file) {
            //下载完成将下载的路径发送Activity
            Intent intent = new Intent(MainActivity.FINISH);
            intent.putExtra("file_path",file.getAbsolutePath());
            manager.sendBroadcast(intent);
            task=null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //边下载边发送数据
            Intent intent = new Intent(MainActivity.DOWNLOADPROCESS);
            intent.putExtra("progress",values[0]);
            manager.sendBroadcast(intent);
        }

        @Override
        protected File doInBackground(String... params) {
            File dir = new File(DOWN_DIR);
            if(!dir.exists()){
                dir.mkdir();
            }
            //获取文件名
            String fileName = params[1];
            InputStream inputStream=null;
            OutputStream outputStream=null;
            try {
                URL url = new URL(params[0]);
                try {
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(4000);
                    if(connection.getResponseCode()==200){
                        inputStream = connection.getInputStream();
                        int len = 0;
                        byte[] buf = new byte[1024];
                        File file = new File(dir,fileName);
                        outputStream = new FileOutputStream(file);
                        //用来计算下载进度
                        int length=0;
                        int total=connection.getContentLength();
                        while ((len=inputStream.read(buf))!=-1){
                            outputStream.write(buf,0,len);
                            length+=len;
                            publishProgress((int)(((float)length/total)*100));
                        }
                        return file;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null||outputStream!=null){
                    try {
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
