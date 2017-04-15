package com.example.dagger2demo;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by Administrator on 2017/2/4.
 */
@Module
public class AppModule {
    @Provides
    public Car getCar(){
        return new Car();
    }
    @Provides
    public IUser getUser(Car car){
        return new User(car,"李四");
    }
}
