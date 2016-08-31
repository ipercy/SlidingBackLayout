package com.isky45.SlidingBackLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/**
 * User: hqs
 * Date: 2016/2/26
 * Time: 16:20
 */
public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int startX;
    private int startY;
    private int endX;
    private int endY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endX = (int) event.getX();
                endY = (int) event.getY();

                if (endY - startY<20 && endX - startX > 100){
                    finish();
                    overridePendingTransition(0,R.anim.out);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}