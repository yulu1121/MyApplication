package com.example.downloadcotinue.sqlopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by Administrator on 2017/1/12.
 */

public class DownLoadSqlHelper extends SQLiteOpenHelper {
    public static final String DOWNLOAD_TB = "download";
    public static final int DL_VERSION = 1;
    public static final String CREATE_DOWNLOAD = "create table "+DOWNLOAD_TB+
            " (_id integer primary key autoincrement," +
            "fileName text," +
            "url text," +
            "progress integer," +
            "length integer)";
    public DownLoadSqlHelper(Context context){
        this(context,DOWNLOAD_TB,null,DL_VERSION);
    }
    public DownLoadSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DOWNLOAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
