// IMyAidlInterface.aidl
package com.example.musicservice.aidl;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
   void play(String path);
   void pause();
   void resume();
   void stop();
}
