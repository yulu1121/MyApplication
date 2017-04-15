package com.example.mvpmodetraning.model;

/**
 * Model层用来处理数据的请求
 * Created by Administrator on 2017/1/16.
 */

public interface IMainModel {
    void queryData();
    /**
     * 用来向presenter发送数据的鸽子
     */
   interface IModelCallBack{
        void setResult(String result);
    }
}
