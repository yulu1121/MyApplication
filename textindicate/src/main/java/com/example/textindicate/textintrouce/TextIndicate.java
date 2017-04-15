package com.example.textindicate.textintrouce;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.textindicate.MainActivity;

/**
 *
 * Created by Administrator on 2017/1/10.
 */

public class TextIndicate extends LinearLayout {
    private LocalBroadcastManager manager;
    public TextIndicate(Context context) {
     this(context,null);
    }

    public TextIndicate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs){
        for (int i = 0; i < 26; i++) {
            char ch = (char) ('A'+i);
            TextView text = new TextView(context);
            text.setText(String.valueOf(ch));
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
            this.addView(text);
        }
    }
    private int currentChild = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                int count = getChildCount();
                for (int i = 0; i <count ; i++) {
                    TextView childAt = (TextView) getChildAt(i);
                    if(currentChild==i){
                        continue;
                    }
                    if(x>=childAt.getLeft()&&x<=childAt.getRight()){
                        if (y>=childAt.getTop()&&y<=childAt.getBottom()){
                            Intent intent = new Intent(MainActivity.ACTION_NAME);
                            manager = LocalBroadcastManager.getInstance(getContext());
                            intent.putExtra("name",childAt.getText().toString());
                            manager.sendBroadcast(intent);
                            currentChild=i;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
