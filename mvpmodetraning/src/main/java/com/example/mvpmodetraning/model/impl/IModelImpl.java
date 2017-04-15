package com.example.mvpmodetraning.model.impl;

import com.example.mvpmodetraning.http.AsyncTaskTool;
import com.example.mvpmodetraning.model.IMainModel;

/**
 *
 * Created by Administrator on 2017/1/16.
 */

public class IModelImpl implements IMainModel {
    public static final String URL = "http://www.1688wan.com/majax.action?method=getGiftList";
    private IModelCallBack modelCallBack;
    public IModelImpl(IModelCallBack modelCallBack){
        this.modelCallBack = modelCallBack;
    }
    @Override
    public void queryData() {
        AsyncTaskTool.load(URL).post("pageno=1").execute(new AsyncTaskTool.IMyCallback() {
            @Override
            public void success(String result) {
                modelCallBack.setResult(result);
            }
        });
    }
}