package com.example.downloadcotinue.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.downloadcotinue.enties.DownLoadObj;
import com.example.downloadcotinue.sqlopenhelper.DownLoadSqlHelper;

/**
 * 数据库实现类
 * Created by Administrator on 2017/1/12.
 */

public class DownLoadDaoImpl implements DownLoadDao {
    private SQLiteDatabase database;
    public DownLoadDaoImpl(Context context){
        if(database==null){
            database=new DownLoadSqlHelper(context).getWritableDatabase();
        }
    }
    @Override
    public void saveDownLoadObj(DownLoadObj obj) {
        database.execSQL("insert into "+DownLoadSqlHelper.DOWNLOAD_TB+
                " (fileName,url,progress,length)"+" values(?,?,?,?)",
                new Object[]{obj.getFileName(),obj.getUrl(),obj.getProgress(),obj.getLength()});
    }

    @Override
    public DownLoadObj queryDownLoad(String url) {
        Cursor cursor = database.rawQuery("select * from " + DownLoadSqlHelper.DOWNLOAD_TB+" where url=?", new String[]{url});
        if(cursor.moveToFirst()){
            DownLoadObj obj = new DownLoadObj();
            obj.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            obj.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            obj.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            obj.setProgress(cursor.getLong(cursor.getColumnIndex("progress")));
            obj.setLength(cursor.getLong(cursor.getColumnIndex("length")));
            return obj;
        }
        cursor.close();
        return null;
    }

    @Override
    public void updateProgress(String url, long progress) {
        database.execSQL("update "+DownLoadSqlHelper.DOWNLOAD_TB
                +" set progress=?"+" where url=?",new Object[]{progress,url});
    }

    @Override
    public void updateLength(String url, long length) {
        database.execSQL("update "+DownLoadSqlHelper.DOWNLOAD_TB
                    +" set length=?"+" where url=?",new Object[]{length,url});
    }

    @Override
    public void deleteDownLoadObj(String url) {
        database.execSQL("delete from "+DownLoadSqlHelper.DOWNLOAD_TB+" where url=?"
                    ,new Object[]{url});
    }

    @Override
    public void closeDb() {
        database.close();
    }
}
