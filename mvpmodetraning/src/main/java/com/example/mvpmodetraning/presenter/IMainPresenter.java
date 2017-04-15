package com.example.mvpmodetraning.presenter;

/**
 *
 * Created by Administrator on 2017/1/16.
 */

public interface IMainPresenter {
    public void querList();

    /**
     * 此接口用来在Presenterg和View中之间进行数据加载
     */
    public interface IPresenterCallBack{
        public void refreshView(String data);
    }
}
