package com.example.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/**
 *
 * Created by Administrator on 2017/2/15.
 */

public class MySqlOpenHelper extends AndroidDaoMaster.DevOpenHelper {

    public MySqlOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        AndroidDaoMaster.createAllTables(db,true);
    }
}
