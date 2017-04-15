package com.example.gesturetrain.gameutils;

import android.content.Context;

/**
 * 保存分数
 * Created by Administrator on 2017/1/11.
 */

public class GameUtils {
    public static void saveScore(Context context,int score){
        context.getSharedPreferences("snake",Context.MODE_PRIVATE)
                .edit()
                .putInt("score",score)
                .apply();
    }
    public static int readScore(Context context){
        return context.getSharedPreferences("snake",Context.MODE_PRIVATE)
                .getInt("score",0);
    }
}
