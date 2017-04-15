package com.example.gesturetrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.gesturetrain.snakegame.SnakeView;

public class MainActivity extends AppCompatActivity {
    private GestureDetector detector;
    public static final int MINI_DISTANCE = 40;
    public static final int MINI_SPEED = 40;
    private SnakeView snakeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        snakeView = new SnakeView(this,display.getWidth(),display.getHeight());
        setContentView(snakeView);
        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e2.getX()-e1.getX()>=MINI_DISTANCE&&Math.abs(velocityX)>=MINI_SPEED){
                    //Toast.makeText(MainActivity.this, "往右滑", Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(SnakeView.RIGHT);
                }else if(e1.getX()-e2.getX()>=MINI_DISTANCE&&Math.abs(velocityX)>=MINI_SPEED){
                    //Toast.makeText(MainActivity.this, "往左滑", Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(SnakeView.LEFT);
                }else if(e2.getY()-e1.getY()>=MINI_DISTANCE&&Math.abs(velocityY)>=MINI_SPEED){
                    //Toast.makeText(MainActivity.this, "往下滑", Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(SnakeView.DOWN);
                }else if(e1.getY()-e2.getY()>=MINI_DISTANCE&&Math.abs(velocityY)>=MINI_SPEED){
                    //Toast.makeText(MainActivity.this, "往上滑", Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(SnakeView.UP);
                }

                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        snakeView.stopHandler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }
}
