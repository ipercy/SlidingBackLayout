package com.isky45.SlidingBackLayout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * User: hqs
 * Date: 2016/2/29
 * Time: 16:31
 */
public class SlidingBackLayout extends FrameLayout {

    private int touchSlop;//滑动和点击事件的临界点,判断此时是否属于滑动事件
    private Scroller scroller;
    private int startX;
    private int startY;
    private int tempX;
    private Activity activity;
    private int viewWidth;
    private boolean isSliding;//判断是否在滑动
    private boolean isFinish;//用于判断是否该结束activity

    public SlidingBackLayout(Context context) {
        this(context,null);
    }

    public SlidingBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.getTouchSlop();
        scroller = new Scroller(context);
    }

    public void replaceRootLayer(Activity activity){
        this.activity = activity;
        ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
        View root = vg.getChildAt(0);
        //不能与下面的addView调换顺序，因为在未remove前，root是有parent的，若这时调用this.addView(root)就相当于把this当作root的parent
        vg.removeView(root);
        this.addView(root);
        vg.addView(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = tempX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                //满足此条件时拦截给自己View的onTouchEvent事件进行消费
                if (moveX - startX > touchSlop && Math.abs((int)ev.getRawY() - startY) < touchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            //当触摸子view时，onInterceptTouchEvent的ACTION_DOWN事件不会传到onTouchEvent的ACTION_DOWN
            //而是交由到子view的dispatchTouchEvent进行分发
//            case MotionEvent.ACTION_DOWN:
//                startX = tempX = (int) event.getRawX();
//                startY = (int) event.getRawY();
//                //如果view没有对ACTION_DOWN进行消费，之后的其他事件不会传递过来。
//                return true;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;//负值表示向右滑动
                tempX = moveX;

                if (moveX - startX > touchSlop && Math.abs((int)event.getRawY() - startY) < touchSlop){
                    isSliding = true;
                }
                if (moveX - startX >= 0 && isSliding){
                    scrollBy(deltaX,0);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                endX = (int) event.getRawX();
//                endY = (int) event.getRawY();
                isSliding = false;

                //getScrollX()等价于 endX-startX,区别在于右滑getScrollX()为负值
                if (getScrollX() <= -viewWidth/2){
                    isFinish = true;
                    scrollToClose();
                }else {
                    isFinish = false;
                    scrollToBack();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            viewWidth = getWidth();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();

            if (scroller.isFinished() && isFinish){
                activity.finish();
            }
        }
    }

    private void scrollToClose(){
        int delta = viewWidth + getScrollX(); //getScrollX()右滑是负数，该值就是剩下未滑动的距离
        scroller.startScroll(getScrollX(),0,-delta+1,0,Math.abs(delta));
        postInvalidate();
    }

    private void scrollToBack(){
        int delta = getScrollX();//该值为负数
        scroller.startScroll(delta,0,-delta,0,Math.abs(delta));
        postInvalidate();
    }
}
