package com.example.selfview.selfdefineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.selfview.R;

/**
 *
 * Created by Administrator on 2017/1/10.
 */

public class SelfEditText extends EditText {
    private Paint mpaint;

    public SelfEditText(Context context) {
        this(context,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public SelfEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context,attrs);
    }
    private void initPaint(Context context, AttributeSet attrs){
        mpaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelfEditText);
        int color = typedArray.getColor(R.styleable.SelfEditText_lineColor, Color.BLUE);
        int integer = typedArray.getInteger(R.styleable.SelfEditText_lineHeight, 3);
        mpaint.setColor(color);
        mpaint.setStrokeWidth(integer);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int width = getWidth()-right;
        int height = getLineHeight();
        int top = getPaddingTop();
        int count = getLineCount();
        for (int i = 0; i <count; i++) {
            int line = top+(i+1)*height;
            canvas.drawLine(left,line,width,line,mpaint);
        }
    }
}
