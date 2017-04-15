package com.example.dagger2demo;

import android.util.Log;

/**
 *
 * Created by Administrator on 2017/2/4.
 */

public class User implements IUser{
    private String name;
    private Car car;
    public User(String name) {
        this.name = name;
    }
    public User(Car car,String name){
        this.car = car;
        this.name = name;
    }
    public void say(){
        car.drive(name);
        Log.e("xxx","你好，这是User"+name);
    }
}
