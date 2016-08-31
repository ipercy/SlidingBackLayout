package com.isky45.SlidingBackLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyActivity extends Activity {

    private TextView textView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.hello);
    }

    public void onClick(View view){
        if (view.getId() == R.id.click){
            ViewGroup v = (ViewGroup) getWindow().getDecorView();
//            int count = v.getChildCount();
//            startActivity(new Intent(this, SecondActivity.class));
            startActivity(new Intent(this, ThirdActivity.class));

        }
    }
}
