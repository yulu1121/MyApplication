package com.example.downloadcotinue.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.downloadcotinue.MainActivity;
import com.example.downloadcotinue.dao.DownLoadDao;
import com.example.downloadcotinue.dao.DownLoadDaoImpl;
import com.example.downloadcotinue.enties.DownLoadObj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Created by Administrator on 2017/1/12.
 */

public class DownLoadService extends Service {
    //apk放置的位置
    public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()+
            File.separator+"downloads";
    //开始的命令
    public static final String START_CMD = "start";
    public static final String STOP_CMD = "stop";
    //广播
    private LocalBroadcastManager manager;
    //线程池管理线程
    private ExecutorService executorService;
    //存放下载任务的集合
    private Map<String,DownLoadTask> loadTaskMap;
    //数据库实现对象'
    private DownLoadDao dao;
    private int actualLength;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dao = new DownLoadDaoImpl(this);
        manager = LocalBroadcastManager.getInstance(this);
        executorService = Executors.newCachedThreadPool();
        loadTaskMap = new HashMap<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cmd = intent.getStringExtra("cmd");
        String url = intent.getStringExtra("url");
        String fileName = intent.getStringExtra("fileName");
        if(START_CMD.equals(cmd)){
            //从集合中获取下载任务
            DownLoadTask loadTask = loadTaskMap.get(url);
            //如果任务已经启动，就跳过
            if(loadTask!=null&&!loadTask.isStop()){
                    return START_NOT_STICKY;
            }
            //查询下载信息
            DownLoadObj loadObj = dao.queryDownLoad(url);
            if(loadObj==null){
                loadObj = new DownLoadObj(0,fileName,url,0,0);
                //保存到数据库中
                dao.saveDownLoadObj(loadObj);
            }
            //创建下载任务
            loadTask = new DownLoadTask(loadObj);
            //启动下载任务
            executorService.execute(loadTask);
            //添加到Map中，方便以后多任务下载
            loadTaskMap.put(url,loadTask);
        }else if(STOP_CMD.equals(cmd)){
           DownLoadTask task= loadTaskMap.get(url);
            if(task!=null){
                task.stop();
            }
        }
        return START_NOT_STICKY;
    }

    class DownLoadTask implements Runnable{
        private DownLoadObj obj;//下载对象信息
        private boolean stop = false;//默认为False
        public DownLoadTask(DownLoadObj obj){
            this.obj = obj;
        }
        public void stop(){
            stop = true;
        }
        public boolean isStop(){
            return stop;
        }
        @Override
        public void run() {
            stop = false;
            //新建下载目录
            File dir = new File(DOWNLOAD_DIR);
            if(!dir.exists()){
               dir.mkdir();
            }
            File file = new File(dir,obj.getFileName());
            RandomAccessFile raf = null;
            InputStream inputStream = null;
            try {
                raf = new RandomAccessFile(file,"rwd");
                //进行联网
                String objUrl = obj.getUrl();
                try {
                    URL url = new URL(objUrl);
                    try {
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        //如果是第一次下载，设置文件长度
                        if(obj.getLength()==0&&obj.getProgress()==0){
                            actualLength= connection.getContentLength();
                            raf.setLength(actualLength);
                            //更新数据库
                            dao.updateLength(objUrl,actualLength);
                            obj.setLength(actualLength);
                        }else {
                            //不是第一次下载
                            connection.setRequestProperty("Range", String.valueOf(obj.getProgress()));
                            //设置本地文件的写入位置
                            raf.seek(obj.getProgress());
                        }
                        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK||
                                connection.getResponseCode()==HttpURLConnection.HTTP_PARTIAL){
                            inputStream = connection.getInputStream();
                            int length = 0;
                            byte[] buf = new byte[1024];
                            long total=obj.getProgress();//加载进度
                            long start = System.currentTimeMillis();
                            Intent intent = new Intent();
                            while((length=inputStream.read(buf))!=-1){
                                raf.write(buf,0,length);
                                total+=length;
                                long end = System.currentTimeMillis();
                                if(end-start>500){
                                    start = end;
                                    //保存进度到数据库中
                                    dao.updateProgress(objUrl,total);
                                    //将进度通过广播发送到Ui
                                    intent.setAction(MainActivity.DOWNLOAD_ACTION);
                                    obj.setProgress(total);
                                    intent.putExtra("obj",obj);
                                    manager.sendBroadcast(intent);
                                }
                                if(stop){
                                    break;
                                }
                            }
                            if(!stop){
                                //从数据库中删除
                                dao.deleteDownLoadObj(objUrl);
                                loadTaskMap.remove(objUrl);
                                //发送下载完成的广播
                                intent.setAction(MainActivity.DOWNLOAD_COMPLETE);
                                intent.putExtra("obj",obj);
                                manager.sendBroadcast(intent);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(raf!=null){
                        try {
                            raf.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            }
        }
    }

