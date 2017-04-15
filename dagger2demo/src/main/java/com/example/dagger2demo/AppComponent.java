package com.example.dagger2demo;

import dagger.Component;

/**
 *
 * Created by Administrator on 2017/2/4.
 */
@Component(modules = {AppModule.class})
public interface AppComponent {
   void inject(MainActivity mainActivity);
}
