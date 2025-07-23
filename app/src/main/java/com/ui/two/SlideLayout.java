package com.ui.two;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SlideLayout extends LinearLayout {
    final protected int GESTURE_UP=-1, GESTURE_DOWN=-2, GESTURE_RIGHT=-3, GESTURE_LEFT=-4;

    protected int gesture_counter;
    protected float last_x, last_y;
    protected int[] gesture_history;

    public SlideLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        last_x=last_y=0;
        gesture_history = new int[30];
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnGestureListener(Runnable[] runnable){
        SlideLayout slideLayout = findViewById(R.id.main);
        slideLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getRawX();
                float y = event.getRawY();
                int GESTURE = config(x,y,event);
                if (GESTURE==0) return true;

                Runnable tmp = runnable[GESTURE+4];
                if(tmp!=null)   tmp.run();
                //Log.e("GESTURE_CODE",String.valueOf(GESTURE));
                return true;
            }
        });
    }

    private int config(float x, float y,MotionEvent event){
        if(gesture_counter>=30||event.getAction()==MotionEvent.ACTION_UP)
            return concludeGesture();

        if(x-last_x>0){
            gesture_history[gesture_counter] = GESTURE_RIGHT;
        }else{
            gesture_history[gesture_counter] = GESTURE_LEFT;
        }
        Log.e("Gesture: ", String.valueOf(gesture_history[gesture_counter]));
        gesture_counter++;


        if (y-last_y>0)   gesture_history[gesture_counter] = GESTURE_UP;
        else    gesture_history[gesture_counter] = GESTURE_DOWN;
        gesture_counter++;

        last_y=y;
        last_x=x;
        return 0;
    }

    private int concludeGesture(){
        int[] counter_arr = new int[4];
        for(int i:gesture_history){
            switch (i){
                case GESTURE_DOWN:
                    counter_arr[2]++;break;
                case GESTURE_UP:
                    counter_arr[3]++;
                    break;
                case GESTURE_RIGHT:
                    counter_arr[1]++;
                    break;
                case GESTURE_LEFT:
                    counter_arr[0]++;
                    break;
            }
        }
        int highest=0;
        for(int i=0;i<counter_arr.length;i++){
            if(highest<counter_arr[i])   highest=i;
        }
        return highest-4;
    }
}
