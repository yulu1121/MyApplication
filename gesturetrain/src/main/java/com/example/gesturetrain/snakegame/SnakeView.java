package com.example.gesturetrain.snakegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.gesturetrain.gameutils.GameUtils;

import java.util.LinkedList;
import java.util.Random;

/**\
 *
 * Created by Administrator on 2017/1/11.
 */

public class SnakeView extends View {
    //身体格子的尺寸
    private int mWidth;
    private int mHeight;
    public static final int SIZE = 30;
    private Paint paint;
    public int XCIRLESIZE;
    public int YCIRLESIZE;
    public static final int LEFT = 1;
    private Random random ;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final long GAME_DELAY =300;//延迟时间
    private Thread thread;
    private int mDirection ;
    private Boolean isDead = false;
    private Boolean isRunning = false;//判断线程是否在运行
    private LinkedList<Point> mBody;//蛇身体的单位集合
    private Point mFood;//食物的集合
    private Context mContext;
    private int highestScore=0;
    private AlertDialog dialog;
    public SnakeView(Context context,int width,int height){
        super(context);
        this.mContext = context;
        this.mWidth = width;
        this.mHeight = height;
        initGame();
    }
    //创建食物
    private void createFood(){
        do{
            random = new Random();
            XCIRLESIZE = Math.abs(1+random.nextInt(mWidth/SIZE)-3);
            YCIRLESIZE = Math.abs(1+random.nextInt(mHeight/SIZE)-5);
            mFood = new Point(XCIRLESIZE,YCIRLESIZE);}
        while(mBody.contains(mFood));//避免出现在蛇的身体上
    }
    //判断是否死亡
    private void checkDead(){
        Point head = mBody.getFirst();
        //当超过边界时死亡
        if(head.x<0||head.x>mWidth/SIZE-1||head.y<0
                ||head.y>mHeight/SIZE-2){
            isDead = true;
        }
        //当吃到自身时
        for (int i = 1; i <mBody.size() ; i++) {
            if(mBody.get(i).equals(head)){
                isDead = true;
                break;
            }
        }
    }
    private void initDialog(final Context context,int score){
        TextView textView = new TextView(context);
        textView.setText("你的分数是"+score);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog = new AlertDialog.Builder(context).
                setView(textView)
                .setTitle("你已死亡")
                .setNegativeButton("重新开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initGame();
                        dialog.dismiss();
                    }
                })
                .create();
            dialog.show();

    }
    private void initGame(){
        mBody = new LinkedList<>();
        for(int i=0;i<5;i++){
            mBody.add(new Point(10,10+i));
        }
        paint = new Paint();
        isRunning = true;
        isDead = false;
        mDirection = LEFT;
        highestScore = GameUtils.readScore(mContext);
        createFood();
        if(thread==null){
           thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   while (isRunning){
                       checkDead();
//                       if(isDead){
//                           //保存最高记录
//                           int score = (mBody.size()-5)*10;
//                           if(score>highestScore){
//                               GameUtils.saveScore(mContext,score);
//                           }
//
//                       }
                        postInvalidate();
                       try {
                           thread.sleep(GAME_DELAY);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
           });
        thread.start();
    }
    }

    public void setDirection(int direction){
        switch (direction){
            case DOWN:
                if(mDirection==UP){
                    return;
                }
                break;
            case UP:
                if(mDirection==DOWN){
                    return;
                }
                break;
            case LEFT:
                if(mDirection==RIGHT){
                    return;
                }
                break;
            case RIGHT:
                if(mDirection==LEFT){
                    return;
                }
                break;
        }
        mDirection = direction;
    }
    public void stopHandler(){
        isRunning = false;
         thread= null;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        move();
        paint.setColor(Color.BLUE);
        for(Point point:mBody){
            canvas.drawRect(point.x*SIZE,point.y*SIZE,(point.x+1)*SIZE-1,
                    (point.y+1)*SIZE-1,paint);
        }
        //画食物
        paint.setColor(Color.RED);
        canvas.drawCircle(XCIRLESIZE*SIZE,YCIRLESIZE*SIZE,20,paint);
        //画分数
        paint.setColor(Color.GREEN);
        paint.setTextSize(30);
        int score = (mBody.size()-5)*10;
        canvas.drawText("当前分数:"+score+",最高记录:"+highestScore,30,30,paint);
        checkDead();
        if(isDead){
            if(score>highestScore){
                GameUtils.saveScore(mContext,score);
            }
            initDialog(mContext,score);
            stopHandler();
        }
    }
    private void move(){
        //获得原来的蛇头
        Point head = mBody.getFirst();
        //创建新的蛇头
        Point newHead = new Point(head.x,head.y);
        switch (mDirection){
            case DOWN:
                newHead.y++;
                break;
            case UP:
                newHead.y--;
                break;
            case RIGHT:
                newHead.x++;
                break;
            case LEFT:
                newHead.x--;
                break;
        }
        mBody.addFirst(newHead);
        if(newHead.equals(mFood)){
            createFood();
        }else {
            mBody.removeLast();
        }
    }
}

