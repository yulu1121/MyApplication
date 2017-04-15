package com.example.retrofit_rxjava_demo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * Created by Administrator on 2017/2/9.
 */

public interface AppService {
    //?ad=2&gender=1&generation=2&limit=20&offset=0
    @GET("v2/channels/{num}/items")
    Observable<ChoiceBean> getChoiceBean(@Path("num")int num,
                                         @Query("ad") int ad,
                                         @Query("gender")int gender,
                                         @Query("generation") int generation,
                                         @Query("limit") int limit,
                                         @Query("offset") int offset);
}
