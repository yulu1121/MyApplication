package com.example.downloadcotinue.dao;

import com.example.downloadcotinue.enties.DownLoadObj;

/**
 * 公有接口，定义一些方法
 * Created by Administrator on 2017/1/12.
 */

public interface DownLoadDao {

    /**保存下载信息
     * @param obj
     */
    void saveDownLoadObj(DownLoadObj obj);
    /**
     * 通过Url查询下载信息
     * @param url
     */
    DownLoadObj queryDownLoad(String url);

    /**
     * 更新下载进度
     * @param url
     * @param progress
     */
    void updateProgress(String url,long progress);


    /**
     * 更新总长度
     * @param url
     * @param length
     */
    void updateLength(String url,long length);


    /**
     * 删除对象
     * @param url
     */
    void deleteDownLoadObj(String url);

    /**
     * 关闭数据库
     */
    void closeDb();
}
