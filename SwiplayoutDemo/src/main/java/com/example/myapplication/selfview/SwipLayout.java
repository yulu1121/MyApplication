package com.example.myapplication.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于实现自定义左右滑动的swiplayout
 * Created by yulu on 2017/4/15.
 */

public class SwipLayout extends LinearLayout {
    private Scroller scroller;
    //触摸滑动的距离
    private int mTouchSlop;
    //右边隐藏控件的宽度
    private int rightViewWidth;
    //展开状态
    public static final int EXPAND = 0;
    //缩放狂态
    public static final int SHRINK=1;
    //是否水平移动,默认false
    Boolean isHorizontalMove;
    float startX;
    float startY;
    float curX;
    float curY;
    float lastX;
    public SwipLayout(Context context) {
        this(context,null);
    }

    public SwipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(HORIZONTAL);
        scroller = new Scroller(context);
        //默认值为8,在可滑动的控件中用于区别单击子控件和滑动操作的一个伐值
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public void SimulateScroll(int type){
        int dx =0;
        switch (type){
            case EXPAND:
                dx = rightViewWidth-getScrollX();
                break;
            case SHRINK:
                dx = 0-getScrollX();
                break;
            default:
                break;
        }

        scroller.startScroll(getScrollX(),0,dx,0,Math.abs(dx)/2);
        invalidate();
    }

    //在ListView中使用
    static List<SwipLayout> swipLayouts = new ArrayList<>();
    public  static void addSwipeView(SwipLayout v){
        if(null==v){
            return;
        }
        swipLayouts.add(v);
    }
    public static void removeSwipeView(SwipLayout v){
        if(null==v){
            return;
        }
        v.SimulateScroll(SwipLayout.SHRINK);
    }

    /**
     * 缩放所有的子控件
     */
    private void shrinkAllView(){
        for(SwipLayout s :swipLayouts){
            if(null==s){
                swipLayouts.remove(s);
                continue;
            }else {
                s.SimulateScroll(SwipLayout.SHRINK);
            }

        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //隐藏的控件在布局的右边第二个位置
        View view = this.getChildAt(1);
        rightViewWidth = view.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isHorizontalMove){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(isHorizontalMove){
                    curX = event.getX();
                    float dX = curX - lastX;
                    lastX = curX;
                    int disX = getScrollX() - (int)dX;
                    if(disX<0){

                        scrollTo(0, 0);
                    }

                    else if(disX>rightViewWidth){
                        scrollTo(rightViewWidth,0);
                    }
                    else {
                        scrollTo(disX, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float dis =endX -startX;

                if(dis<0){
                    SimulateScroll(EXPAND);
                }

                else{
                    SimulateScroll(SHRINK);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                disallowParentsInterceptTouchEvent(getParent());
                //初始状态
                startX = ev.getX();
                startY = ev.getY();
                isHorizontalMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isHorizontalMove){
                    curX = ev.getX();
                    curY = ev.getY();
                    //移动的距离
                    float dX = curX - startX;
                    float dY = curY - startY;
                    if(dX*dX+dY*dY>mTouchSlop*mTouchSlop){
                        //取绝对值，如果Y坐标大于X坐标，说明是向上或向下，而不是
                        //左右滑动，就要缩放隐藏控件
                        if(Math.abs(dY)>Math.abs(dX)){
                            //事件由父控件进行消费
                            allowParentsInterceptTouchEvent(getParent());
                            shrinkAllView();
                        }else {
                            //否则就向左滑动
                            isHorizontalMove = true;
                            lastX = curX;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 不让父类阻止事件的分发
     * @param parent
     */
    private void disallowParentsInterceptTouchEvent(ViewParent parent) {
        if (null == parent) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(true);
        disallowParentsInterceptTouchEvent(parent.getParent());
    }

    @Override
    public void computeScroll() {
        //更行UI滑动
        if (scroller.computeScrollOffset()) {

            scrollTo(scroller.getCurrX(), 0);

            invalidate();
        }
    }

    /**
     * 让父类阻止事件的分发
     * @param parent
     */
    private void allowParentsInterceptTouchEvent(ViewParent parent) {
        if (null == parent) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(false);
        allowParentsInterceptTouchEvent(parent.getParent());
    }
}
