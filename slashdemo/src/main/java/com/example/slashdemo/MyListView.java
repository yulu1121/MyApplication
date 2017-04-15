package com.example.slashdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 *
 * Created by Administrator on 2017/2/14.
 */

public class MyListView extends ListView {
    private float startX,startY;
    private Context context;
    private float maxDistance;
    private boolean isShow;
    private View view;
    private float distance;
    private float middleValue;
    public MyListView(Context context) {
        this(context,null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        maxDistance = dp2Px(200);
        middleValue = dp2Px(100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if (view != null) {
                    float endX = ev.getX();
                    float distanceY = startY - ev.getY();
                    distance = startX - endX;
                    if (Math.abs(distanceY) < distance && distance > 0 && distance <= maxDistance) {
                        view.scrollTo((int) distance,0);
                        isShow = true;
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(isShow){
                    isShow = false;
                    view.scrollTo(0,0);
                }else {
                    startX= ev.getX();
                    startY= ev.getY();
                    int position = pointToPosition((int) startX, (int) startY);
                    view = getChildAt(position-getFirstVisiblePosition());
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isShow) {
                    if (distance < middleValue) {
                        isShow = false;
                        view.scrollTo(0,0);
                    } else {
                        view.scrollTo((int)maxDistance,0);
                    }
                }
                break;
        }
        if(isShow){
            return true;
        }
        return super.onTouchEvent(ev);
    }
    private float dp2Px(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density + 0.5F;
    }
}
