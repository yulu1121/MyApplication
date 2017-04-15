package com.example.mvpmodetraning.presenter.impl;

import com.example.mvpmodetraning.model.IMainModel;
import com.example.mvpmodetraning.model.impl.IModelImpl;
import com.example.mvpmodetraning.presenter.IMainPresenter;

/**
 *
 * Created by Administrator on 2017/1/16.
 */

public class IMainPresenterImpl implements IMainPresenter,IMainModel.IModelCallBack{
    private IMainModel mainModel;
    private IPresenterCallBack callBack;
    public IMainPresenterImpl(IPresenterCallBack callBack) {
        this.callBack = callBack;
        mainModel = new IModelImpl(this);
    }
    @Override
    public void querList() {
        mainModel.queryData();
    }

    @Override
    public void setResult(String result) {
        callBack.refreshView(result);
    }
}
