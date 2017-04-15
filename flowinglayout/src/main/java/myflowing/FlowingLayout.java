package myflowing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * Created by Administrator on 2017/1/10.
 */

public class FlowingLayout extends ViewGroup {
    public FlowingLayout(Context context) {
        this(context,null);
    }

    public FlowingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int width = r-l;//布局的宽度
        int childLeft = 0;//子控件的左边
        int childTop = 0;//子控件的上边
        int maxHeight = 0;//子控件中最高的高度
        for (int i = 0; i <count ; i++) {
            View childAt = getChildAt(i);
            int childWidth = childAt.getMeasuredWidth();//获得测量的宽度
            int childHeight = childAt.getMeasuredHeight();//获得测量的高度
            int childRight = childLeft+childWidth;//获得右边
            int childBottom = childTop+childHeight;
            //右边是否超过了布局的宽度，换行
            if(childRight>width){
                childLeft = 0;
                childRight = childLeft+childWidth;
                childBottom = childTop+childHeight;
                childTop = childTop+maxHeight;//换行后的控件高度
            }else {
                //计算最大高度
                maxHeight = Math.max(maxHeight,childHeight);
            }
            childAt.layout(childLeft,childTop,childRight,childBottom);
            //前一个控件的右边赋值给后一个控件的左边
            childLeft = childRight;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子控件的高和宽
        this.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}
