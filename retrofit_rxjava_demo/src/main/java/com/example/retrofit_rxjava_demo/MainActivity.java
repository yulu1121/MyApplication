package com.example.retrofit_rxjava_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 导包:
 *  compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
     compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
    compile 'io.reactivex:rxandroid:1.2.1'
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG ="xxx" ;
    private Map<String,List<ChoiceBean.DataBean.ItemsBean>> datas = new LinkedHashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.liwushuo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        AppService appService = retrofit.create(AppService.class);
        appService.getChoiceBean(101,2,1,2,20,0)
                .map(new Func1<ChoiceBean, List<ChoiceBean.DataBean.ItemsBean>>() {
                    @Override
                    public List<ChoiceBean.DataBean.ItemsBean> call(ChoiceBean selectionBean) {
                        return selectionBean.getData().getItems();
                    }
                })
                .flatMap(new Func1<List<ChoiceBean.DataBean.ItemsBean>, Observable<ChoiceBean.DataBean.ItemsBean>>() {
                    @Override
                    public Observable<ChoiceBean.DataBean.ItemsBean> call(List<ChoiceBean.DataBean.ItemsBean> itemsBeen) {
                        return Observable.from(itemsBeen);
                    }
                })
                .map(new Func1<ChoiceBean.DataBean.ItemsBean, Boolean>() {
                    @Override
                    public Boolean call(ChoiceBean.DataBean.ItemsBean itemsBean) {
                        Log.i(TAG, "call: " + Thread.currentThread().getName());
                        Log.i(TAG, "call: " + itemsBean.getTitle());
                        String formatTime = formatTime(itemsBean.getCreated_at());
                        if (!datas.containsKey(formatTime)) {
                            datas.put(formatTime,new ArrayList<ChoiceBean.DataBean.ItemsBean>());
                        }
                        datas.get(formatTime).add(itemsBean);
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                               @Override
                               public void call(Boolean aBoolean) {

                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: "+"complete" );
                            }
                        });
    }
    private String formatTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(time*1000));
    }
}
