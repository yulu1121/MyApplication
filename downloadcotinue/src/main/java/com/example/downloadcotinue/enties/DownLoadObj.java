package com.example.downloadcotinue.enties;

import java.io.Serializable;

/**
 *
 * Created by Administrator on 2017/1/12.
 */

public class DownLoadObj implements Serializable{
    private int id;
    private String fileName;
    private String url;
    private long length;
    private long progress;

    public DownLoadObj(int id, String fileName, String url, long length, long progress) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.length = length;
        this.progress = progress;
    }

    public DownLoadObj() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "DownLoadObj{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", progress=" + progress +
                '}';
    }
}
