package com.example.loginmodule;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *
 * Created by Administrator on 2017/2/10.
 */

public interface AppService {
    //http://192.168.53.24:8080/
    @FormUrlEncoded
    @POST("login.jsp")
    Call<ResponseBody> login(@Field("username")String name,@Field("userpass")String pass,@Field("type")String type);
}
