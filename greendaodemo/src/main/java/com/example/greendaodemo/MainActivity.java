package com.example.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.greendao.AndroidDaoMaster;
import com.example.greendao.AndroidDaoSession;
import com.example.greendao.MySqlOpenHelper;
import com.example.greendao.User;
import com.example.greendao.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySqlOpenHelper openHelper = new MySqlOpenHelper(this, "androiddemo");
        SQLiteDatabase readableDatabase = openHelper.getReadableDatabase();
        AndroidDaoMaster daoMaster = new AndroidDaoMaster(readableDatabase);
        AndroidDaoSession session = daoMaster.newSession();
        dao = session.getUserDao();
    }

    public void insert(View view) {
        User user = new User();
        user.setUser_name("zhangsan");
        user.setUser_age(18);
        dao.insert(user);
    }

    public void update(View view) {
        User user = new User();
        user.setId(1L);
        user.setUser_name("张三");
        dao.update(user);
    }

    public void delete(View view) {
        dao.deleteByKey(2L);
    }

    public void query(View view) {
        List<User> users = dao.loadAll();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Log.e("xxx",user.getUser_name());
        }
    }
}
